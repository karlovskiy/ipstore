package ipstore.breadcrumb;

import java.io.Serializable;

/**
 * Here will be javadoc
 *
 * @author ravenstar
 * @since 4.1, 3/8/14
 */
public class BreadcrumbItem implements Serializable {

    private final String requestURI;
    private final String label;
    private boolean current;

    public BreadcrumbItem(String requestURI, String label) {
        this.requestURI = requestURI;
        this.label = label;
    }

    public String getRequestURI() {
        return requestURI;
    }

    public String getLabel() {
        return label;
    }

    public boolean isCurrent() {
        return current;
    }

    public void setCurrent(boolean current) {
        this.current = current;
    }

    @Override
    public String toString() {
        return "BreadcrumbItem{" +
                "requestURI='" + requestURI + '\'' +
                ", label='" + label + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BreadcrumbItem that = (BreadcrumbItem) o;

        if (label != null ? !label.equals(that.label) : that.label != null) return false;
        if (requestURI != null ? !requestURI.equals(that.requestURI) : that.requestURI != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = requestURI != null ? requestURI.hashCode() : 0;
        result = 31 * result + (label != null ? label.hashCode() : 0);
        return result;
    }
}
