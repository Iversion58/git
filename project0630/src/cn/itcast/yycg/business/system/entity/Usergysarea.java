package cn.itcast.yycg.business.system.entity;

// Generated 2015-9-21 14:40:14 by Hibernate Tools 4.0.0

/**
 * Usergysarea generated by hbm2java
 */
public class Usergysarea implements java.io.Serializable {

	private String areaid;
	private String usergysid;
	private String vchar1;
	private String vchar2;
	private String vchar3;

	public Usergysarea() {
	}

	public Usergysarea(String areaid, String usergysid) {
		this.areaid = areaid;
		this.usergysid = usergysid;
	}

	public Usergysarea(String areaid, String usergysid, String vchar1,
			String vchar2, String vchar3) {
		this.areaid = areaid;
		this.usergysid = usergysid;
		this.vchar1 = vchar1;
		this.vchar2 = vchar2;
		this.vchar3 = vchar3;
	}

	public String getAreaid() {
		return this.areaid;
	}

	public void setAreaid(String areaid) {
		this.areaid = areaid;
	}

	public String getUsergysid() {
		return this.usergysid;
	}

	public void setUsergysid(String usergysid) {
		this.usergysid = usergysid;
	}

	public String getVchar1() {
		return this.vchar1;
	}

	public void setVchar1(String vchar1) {
		this.vchar1 = vchar1;
	}

	public String getVchar2() {
		return this.vchar2;
	}

	public void setVchar2(String vchar2) {
		this.vchar2 = vchar2;
	}

	public String getVchar3() {
		return this.vchar3;
	}

	public void setVchar3(String vchar3) {
		this.vchar3 = vchar3;
	}

}
