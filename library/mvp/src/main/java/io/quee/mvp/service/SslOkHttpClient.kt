package io.quee.mvp.service

import okhttp3.OkHttpClient
import java.io.IOException
import java.io.InputStream
import java.security.GeneralSecurityException
import java.security.KeyStore
import java.security.cert.CertificateFactory
import javax.net.ssl.*

class SslOkHttpClient private constructor() {
    fun create(inputStream: InputStream, okHttpClient: OkHttpClient): OkHttpClient {
        return try {
            val keyAndTrustManagers = trustManagerForCertificates(inputStream)
            val sslContext = SSLContext.getInstance("TLS")
            sslContext.init(
                keyAndTrustManagers.keyManagers,
                keyAndTrustManagers.trustManagers,
                null
            )
            val sslSocketFactory = sslContext.socketFactory
            val trustManager =
                keyAndTrustManagers.trustManagers[0] as X509TrustManager
            okHttpClient.newBuilder()
                .sslSocketFactory(sslSocketFactory, trustManager)
                .build()
        } catch (e: GeneralSecurityException) {
            throw RuntimeException(e)
        }
    }

    /**
     * Returns a trust manager that trusts `certificates` and none other. HTTPS services whose
     * certificates have not been signed by these certificates will fail with a `SSLHandshakeException`.
     *
     *
     * This can be used to replace the host platform's built-in trusted certificates with a custom
     * set. This is useful in development where certificate authority-trusted certificates aren't
     * available. Or in production, to avoid reliance on third-party certificate authorities.
     *
     *
     * See also [CertificatePinner], which can limit trusted certificates while still using
     * the host platform's built-in trust store.
     *
     * <h3>Warning: Customizing Trusted Certificates is Dangerous!</h3>
     *
     *
     * Relying on your own trusted certificates limits your server team's ability to update their
     * TLS certificates. By installing a specific set of trusted certificates, you take on additional
     * operational complexity and limit your ability to migrate between certificate authorities. Do
     * not use custom trusted certificates in production without the blessing of your server's TLS
     * administrator.
     */
    @Throws(GeneralSecurityException::class)
    private fun trustManagerForCertificates(inputStream: InputStream): KeyAndTrustManagers {
        val certificateFactory =
            CertificateFactory.getInstance("X.509")
        val certificates =
            certificateFactory.generateCertificates(inputStream)
        require(!certificates.isEmpty()) { "expected non-empty set of trusted certificates" }
        val password = "echeque".toCharArray()
        val keyStore = newEmptyKeyStore(password)
        var index = 0
        for (certificate in certificates) {
            val certificateAlias = Integer.toString(index++)
            keyStore.setCertificateEntry(certificateAlias, certificate)
        }
        val keyManagerFactory =
            KeyManagerFactory.getInstance(
                KeyManagerFactory.getDefaultAlgorithm()
            )
        keyManagerFactory.init(keyStore, password)
        val trustManagerFactory =
            TrustManagerFactory.getInstance(
                TrustManagerFactory.getDefaultAlgorithm()
            )
        trustManagerFactory.init(keyStore)
        return KeyAndTrustManagers(
            keyManagerFactory.keyManagers,
            trustManagerFactory.trustManagers
        )
    }

    @Throws(GeneralSecurityException::class)
    private fun newEmptyKeyStore(password: CharArray): KeyStore {
        return try {
            val keyStore =
                KeyStore.getInstance(KeyStore.getDefaultType())
            keyStore.load(null, password)
            keyStore
        } catch (e: IOException) {
            throw AssertionError(e)
        }
    }

    internal inner class KeyAndTrustManagers(
        val keyManagers: Array<KeyManager>,
        val trustManagers: Array<TrustManager>,
    )

    companion object {
        fun generate(client: OkHttpClient, inputStream: InputStream): OkHttpClient {
            return SslOkHttpClient().create(inputStream, client)
        }
    }
}