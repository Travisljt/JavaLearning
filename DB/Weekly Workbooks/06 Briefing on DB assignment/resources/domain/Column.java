package domain;

public class Column {
    private String dataValue;

    private String dataType;

    public Column() {
        this.dataValue = "";
    }

    public Column(String dataValue) {
        this.dataValue = dataValue;
    }

	public String getDataValue() {
		return dataValue;
	}

	public void setDataValue(String dataValue) {
		this.dataValue = dataValue;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

}
