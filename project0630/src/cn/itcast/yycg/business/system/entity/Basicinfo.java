package cn.itcast.yycg.business.system.entity;

// Generated 2015-9-13 11:11:11 by Hibernate Tools 4.0.0

/**
 * Basicinfo generated by hbm2java
 */
public class Basicinfo implements java.io.Serializable {

	private String id;
	private String name;
	private String value;
	private String type;
	private String tag;
	private String isalive;
	private String vchar1;
	private String vchar3;
	private String vchar2;

	public Basicinfo() {
	}

	public Basicinfo(String id, String name, String value, String type,
			String isalive) {
		this.id = id;
		this.name = name;
		this.value = value;
		this.type = type;
		this.isalive = isalive;
	}

	public Basicinfo(String id, String name, String value, String type,
			String tag, String isalive, String vchar1, String vchar3,
			String vchar2) {
		this.id = id;
		this.name = name;
		this.value = value;
		this.type = type;
		this.tag = tag;
		this.isalive = isalive;
		this.vchar1 = vchar1;
		this.vchar3 = vchar3;
		this.vchar2 = vchar2;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTag() {
		return this.tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getIsalive() {
		return this.isalive;
	}

	public void setIsalive(String isalive) {
		this.isalive = isalive;
	}

	public String getVchar1() {
		return this.vchar1;
	}

	public void setVchar1(String vchar1) {
		this.vchar1 = vchar1;
	}

	public String getVchar3() {
		return this.vchar3;
	}

	public void setVchar3(String vchar3) {
		this.vchar3 = vchar3;
	}

	public String getVchar2() {
		return this.vchar2;
	}

	public void setVchar2(String vchar2) {
		this.vchar2 = vchar2;
	}

}
