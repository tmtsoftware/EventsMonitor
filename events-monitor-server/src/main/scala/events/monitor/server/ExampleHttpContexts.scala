package events.monitor.server

import java.io.InputStream
import java.security.cert.{Certificate, CertificateFactory}
import java.security.{KeyStore, SecureRandom}

import akka.http.scaladsl.HttpsConnectionContext
import javax.net.ssl.{KeyManagerFactory, SSLContext, SSLParameters, TrustManagerFactory}

/**
 * These are HTTPS example configurations that take key material from the resources/key folder.
 */
object ExampleHttpContexts {

  // TODO show example how to obtain pre-configured context from ssl-config


  val exampleServerContext: HttpsConnectionContext = {
    // never put passwords into code!
    val password = "abcdef".toCharArray

    val ks = KeyStore.getInstance("PKCS12")
    ks.load(resourceStream("keys/server.p12"), password)

    val keyManagerFactory = KeyManagerFactory.getInstance("SunX509")
    keyManagerFactory.init(ks, password)

    val context = SSLContext.getInstance("TLS")
    context.init(keyManagerFactory.getKeyManagers, null, new SecureRandom)

    new HttpsConnectionContext(context)
  }

  val exampleClientContext: HttpsConnectionContext = {
    val certStore = KeyStore.getInstance(KeyStore.getDefaultType)
    certStore.load(null, null)
    // only do this if you want to accept a custom root CA. Understand what you are doing!
    certStore.setCertificateEntry("ca", loadX509Certificate("keys/rootCA.crt"))

    val certManagerFactory = TrustManagerFactory.getInstance("SunX509")
    certManagerFactory.init(certStore)

    val context = SSLContext.getInstance("TLS")
    context.init(null, certManagerFactory.getTrustManagers, new SecureRandom)

    val params = new SSLParameters()
    params.setEndpointIdentificationAlgorithm("https")
    new HttpsConnectionContext(context, sslParameters = Some(params))
  }

  def resourceStream(resourceName: String): InputStream = {
    val is = getClass.getClassLoader.getResourceAsStream(resourceName)
    require(is ne null, s"Resource $resourceName not found")
    is
  }

  def loadX509Certificate(resourceName: String): Certificate =
    CertificateFactory.getInstance("X.509").generateCertificate(resourceStream(resourceName))
}
