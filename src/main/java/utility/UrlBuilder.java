package utility;

import org.apache.commons.lang3.StringUtils;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class UrlBuilder {

    private static final String EQUALS = "=";
    private static final String AMPERSAND = "&";
    private static final String QUESTION_MARK = "?";
    private static final String MESSAGE = "message";
    private static final String SUCCESS = "success";
    private static final String ERROR = "error";
    private static final String TRUE = "true";

    private Map<String, String> parameters;
    private String directory;

    public UrlBuilder() {
        parameters = new HashMap<>();
    }

    public UrlBuilder addParameter(final String name, final Object value) {

        var theValue = value.toString();

        if (StringUtils.isNoneBlank(name, theValue)) {
            parameters.put(name, theValue);
        }
        return this;
    }


    public String getUrl(final String directory) {
        this.directory = directory;
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(directory)
                .append(QUESTION_MARK);

        for (Map.Entry entry : parameters.entrySet()) {
            stringBuilder.append(entry.getKey())
                    .append(EQUALS)
                    .append(Utils.URLEncode(entry.getValue().toString()))
                    .append(AMPERSAND);
        }

        stringBuilder.deleteCharAt(stringBuilder.length() - 1);

        return stringBuilder.toString();
    }


    public UrlBuilder addMessage(final String message) {
        addParameter(MESSAGE, Base64.getEncoder().encodeToString(message.getBytes()));
        return this;
    }

    public UrlBuilder addErrorParameter() {
        addParameter(ERROR, TRUE);
        return this;
    }

    public UrlBuilder addSuccessParameter() {
        addParameter(SUCCESS, TRUE);
        return this;
    }

    public UrlBuilder setUrl(String directory) {
        this.directory = directory;
        return this;
    }

    public String getUrl() {
        return this.getUrl(this.directory);
    }

    @Override
    public String toString() {
        return getUrl(this.directory);
    }
}
