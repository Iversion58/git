package cn.itcast.yycg.business.cgd.entity;

import cn.itcast.yycg.business.system.entity.Dictinfo;
import cn.itcast.yycg.business.ypml.entity.Ypxx;

// Generated 2015-9-21 10:27:03 by Hibernate Tools 4.0.0

/**
 * Yycgdmx generated by hbm2java
 */
public class Yycgdmx implements java.io.Serializable {

	private String id;
	private Ypxx ypxx;
	private String ypxxid;
	private Dictinfo dictinfoByCgzt;
	private String cgzt;
//	private Yycgd yycgd;
	private Long yycgdid;
	private Float zbjg;
	private Float jyjg;
	private Integer cgl;
	private Float cgje;
	private String vchar1;
	private String vchar2;
	private String vchar3;
	private String vchar4;
	private String vchar5;

	public Yycgdmx() {
	}

	
	public String getYpxxid() {
		return ypxxid;
	}


	public void setYpxxid(String ypxxid) {
		this.ypxxid = ypxxid;
	}


	public Dictinfo getDictinfoByCgzt() {
		return dictinfoByCgzt;
	}


	public void setDictinfoByCgzt(Dictinfo dictinfoByCgzt) {
		this.dictinfoByCgzt = dictinfoByCgzt;
	}


	public String getCgzt() {
		return cgzt;
	}


	public void setCgzt(String cgzt) {
		this.cgzt = cgzt;
	}


	public Long getYycgdid() {
		return yycgdid;
	}


	public void setYycgdid(Long yycgdid) {
		this.yycgdid = yycgdid;
	}


	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Ypxx getYpxx() {
		return this.ypxx;
	}

	public void setYpxx(Ypxx ypxx) {
		this.ypxx = ypxx;
	}



/*	public Yycgd getYycgd() {
		return this.yycgd;
	}

	public void setYycgd(Yycgd yycgd) {
		this.yycgd = yycgd;
	}*/

	public Float getZbjg() {
		return this.zbjg;
	}

	public void setZbjg(Float zbjg) {
		this.zbjg = zbjg;
	}

	public Float getJyjg() {
		return this.jyjg;
	}

	public void setJyjg(Float jyjg) {
		this.jyjg = jyjg;
	}

	public Integer getCgl() {
		return this.cgl;
	}

	public void setCgl(Integer cgl) {
		this.cgl = cgl;
	}

	public Float getCgje() {
		return this.cgje;
	}

	public void setCgje(Float cgje) {
		this.cgje = cgje;
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

	public String getVchar4() {
		return this.vchar4;
	}

	public void setVchar4(String vchar4) {
		this.vchar4 = vchar4;
	}

	public String getVchar5() {
		return this.vchar5;
	}

	public void setVchar5(String vchar5) {
		this.vchar5 = vchar5;
	}

}
