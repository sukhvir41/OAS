package utility;

import java.util.Base64;

public class UrlParameters {

	private StringBuilder builder;

	public UrlParameters() {
		builder = new StringBuilder();
	}

	public UrlParameters addParamter(String name, String value) {
		if ( builder.length() > 1 ) {
			builder.append( '&' );
		}
		builder.append( name );
		builder.append( '=' );
		builder.append( Utils.URLEncode( value ) );

		return this;
	}


	public String getUrl() {
		builder.insert( 0, '?' );
		return builder.toString();
	}


	public String getUrl(String directory) {
		builder.insert( 0, directory + "?" );
		//builder.insert( directory.length(), '?' );
		return builder.toString();
	}

	public UrlParameters addMessage(String message) {
		//addParamter( "message",  message );
		addParamter( "message", Base64.getEncoder().encodeToString( message.getBytes() ) );
		return this;
	}

	public UrlParameters addErrorParameter() {
		if ( builder.length() > 1 ) {
			builder.append( '&' );
		}
		builder.append( "error=true" );
		return this;
	}

	public UrlParameters addSuccessParameter() {
		if ( builder.length() > 1 ) {
			builder.append( '&' );
		}
		builder.append( "success=true" );
		return this;
	}


}
