package uk.gov.hmrc.mark;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.MessageDigest;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.xml.security.Init;
import org.apache.xml.security.signature.XMLSignatureInput;
import org.apache.xml.security.transforms.Transforms;
import org.bouncycastle.util.encoders.Base64;
import org.w3c.dom.Document;


public abstract class MarkCalculator
{
    /**
     * Name of the default algorithm to use for hashing the mark String. 
     */
	public static final String DEFAULT_SEC_HASH_ALGORITHM = "SHA";

	/**
     * Create a Base64 encoded representation of a mark, created from
     * the given <tt>InputStream</tt>.
     *  
     * @param in
     *      Stream to the data for which to generate a mark.
     * @return
     *      A Base64 encoded mark.
     * 
     * @throws Exception
	 */
	public String createMark(InputStream in) throws Exception
	{
	    //create a Base64 of the digest with the BouncyCastle JCE library
        return toBase64(getMarkBytes(in));
    }
    
    /**
     * Retrieve the raw bytes of a mark, created from the given
     * <tt>InputStream</tt>.
     * 
     * @param in
     *      Stream to the data for which to generate a mark.
     * @param namespace
     * 
     * @return
     *      Raw bytes of the mark.
     *
     * @throws Exception
     */
    protected byte[] getMarkBytes(InputStream in) throws Exception
    {
		Init.init();

		DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		DocumentBuilder db=dbf.newDocumentBuilder();
		Document doc=db.parse(new ByteArrayInputStream(getAlgorithm().getBytes()));

		Transforms transforms = new Transforms(doc.getDocumentElement(), null);

      	XMLSignatureInput input = new XMLSignatureInput(in);
      	XMLSignatureInput result = transforms.performTransforms(input);

		MessageDigest md = MessageDigest.getInstance(DEFAULT_SEC_HASH_ALGORITHM);
		md.update(result.getBytes());

		return md.digest();
	}

	protected abstract String getAlgorithm();
	
	
    /**
     * Utility method to Base64 encode the given byte array.
     * 
     * @param irMarkBytes
     *      This should be an IR Mark in its raw byte format.
     * @return
     *      The given byte array encoded into a Base64 String.
     */
    public static String toBase64(byte[] irMarkBytes)
    {
        return new String(Base64.encode(irMarkBytes));
    }
    
}
