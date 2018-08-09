package otpencryption;

import java.util.Random;

import exceptions.InvalidKeyException;
import exceptions.NullTextException;

public class OTPEncryption {

	private String text;
	private String key = "";
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	
	
	public void generateKey() throws NullTextException {
		if(text != null) {
			Random random = new Random();
			
			for(int i = 0, len = text.length(); i < len; i++) {
				key += String.valueOf((char) random.nextInt(256));
			}
		} else {
			throw new NullTextException("Text cannot be null to generate a key");
		}
	}
	
	public void encrypt() throws InvalidKeyException {
		if(text.length() == key.length()) {
			String aux = "";
			char c;
			
			for(int i = 0, len = text.length(); i < len; i++) {
				if((int) (text.charAt(i) - key.charAt(i)) > 255) {
					c = (char) (text.charAt(i) + key.charAt(i) - 255);
				} else {
					c = (char) (text.charAt(i) + key.charAt(i));
				}
				
				aux += String.valueOf(c);
			}
			
			text = aux;
		} else {
			throw new InvalidKeyException("The key has to be the same length as the text");
		}
	}
	
	public void decrypt() throws InvalidKeyException {
		if(text.length() == key.length()) {
			String aux = "";
			char c;
			
			for(int i = 0, len = text.length(); i < len; i++) {
				if((int) (text.charAt(i) - key.charAt(i)) < 0) {
					c = (char) (text.charAt(i) - key.charAt(i) + 255);
				} else {
					c = (char) (text.charAt(i) - key.charAt(i));
				}
				
				aux += String.valueOf(c);
			}
			
			text = aux;
		} else {
			throw new InvalidKeyException("The key has to be the same length as the text");
		}
	}
	
	public static void main(String args[]) {
		OTPEncryption otp = new OTPEncryption();
		otp.setText("abcde");
		
		try {
			otp.generateKey();
			System.out.println(otp.getKey());
			
			otp.encrypt();
			System.out.println(otp.getText());
			
			otp.decrypt();
			System.out.println(otp.getText());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
