/**
 * 
 */
package data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author benxing
 *
 */
public enum Severity {
	Critical("Critical"),
    Major("Major"),
    Minor("Minor"),
    Cosmetic("Cosmetic");

    private final String severity;

    /**
     * @param text
     */
    @JsonCreator
    private Severity(@JsonProperty("severity")final String severity) {
        this.severity = severity;
    }

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return severity;
    }
}
