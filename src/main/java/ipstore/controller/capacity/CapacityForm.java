package ipstore.controller.capacity;

/**
 * Here will be javadoc
 *
 * @author karlovsky
 * @since 3.0, 8/20/13
 */
public class CapacityForm {

    private String capacityType;
    private String keyword;

    public boolean isEmptyKeyword() {
        return keyword == null || keyword.isEmpty();
    }

    public boolean isEmptyCapacityType() {
        return capacityType == null || capacityType.isEmpty();
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getCapacityType() {
        return capacityType;
    }

    public void setCapacityType(String capacityType) {
        this.capacityType = capacityType;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("capacityType=").append(capacityType)
                .append(", keyword=").append(keyword);
        return result.toString();
    }
}
