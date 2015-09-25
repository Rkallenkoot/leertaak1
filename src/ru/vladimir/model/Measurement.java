package ru.vladimir.model;

import org.jdom2.Element;

import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Measurement {

    private int stn;
    private Date date;
    private Time time;
    private float temp;
    private float dewp;
    private float stp;
    private float slp;
    private float visib;
    private float wdsp;
    private float prcp;
    private float sndp;
    private byte frshtt;
    private float cldc;
    private short wnddir;

    public Measurement(Element el) {
        setStn(Integer.parseInt(el.getChildText("STN")));
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
            setDate(Date.valueOf(el.getChildText("DATE")));
            long ms = sdf.parse(el.getChildText("TIME")).getTime();
            setTime(new Time(ms));

        } catch (ParseException e) {
            System.out.println("Parse Exception in Measurement: " + e.getMessage());
        }
        String temp = el.getChildText("TEMP");
        String dewp = el.getChildText("DEWP");
        String stp = el.getChildText("SLP");
        String slp = el.getChildText("SLP");
        String visib = el.getChildText("VISIB");
        String wdsp = el.getChildText("WDSP");
        String prcp = el.getChildText("PRCP");
        String sndp = el.getChildText("SNDP");
        String frshtt = el.getChildText("FRSHTT");
        String cldc = el.getChildText("CLDC");
        String wnddir = el.getChildText("WNDDIR");

        if (!temp.equals("")) {
            setTemp(Float.parseFloat(temp));
        } else {
            setTemp(Float.MAX_VALUE);
        }

        if (!dewp.equals("")) {
            setDewp(Float.parseFloat(dewp));
        } else {
            setDewp(Float.MAX_VALUE);
        }

        if (!stp.equals("")) {
            setStp(Float.parseFloat(stp));
        } else {
            setStp(Float.MAX_VALUE);
        }

        if (!slp.equals("")) {
            setSlp(Float.parseFloat(slp));
        } else {
            setSlp(Float.MAX_VALUE);
        }

        if (!visib.equals("")) {
            setVisib(Float.parseFloat(visib));
        } else {
            setVisib(Float.MAX_VALUE);
        }

        if (!wdsp.equals("")) {
            setWdsp(Float.parseFloat(wdsp));
        } else {
            setWdsp(Float.MAX_VALUE);
        }

        if (!prcp.equals("")) {
            setPrcp(Float.parseFloat(prcp));
        } else {
            setPrcp(Float.MAX_VALUE);
        }

        if (!sndp.equals("")) {
            setSndp(Float.parseFloat(sndp));
        } else {
            setSndp(Float.MAX_VALUE);
        }

        if (!frshtt.equals("")) {
            setFrshtt(Byte.valueOf(frshtt, 2));
        } else {
            setFrshtt(Byte.parseByte("0"));
        }

        if (!cldc.equals("")) {
            setCldc(Float.parseFloat(cldc));

        } else {
            setCldc(Float.MAX_VALUE);
        }

        if (!wnddir.equals("")) {
            setWnddir(Short.parseShort(wnddir));
        } else {
            setWnddir(Short.MAX_VALUE);
        }

    }

    public int getStn() {
        return stn;
    }

    public void setStn(int stn) {
        this.stn = stn;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public float getTemp() {
        return temp;
    }

    public void setTemp(float temp) {
        this.temp = temp;
    }

    public float getDewp() {
        return dewp;
    }

    public void setDewp(float dewp) {
        this.dewp = dewp;
    }

    public float getStp() {
        return stp;
    }

    public void setStp(float stp) {
        this.stp = stp;
    }

    public float getSlp() {
        return slp;
    }

    public void setSlp(float slp) {
        this.slp = slp;
    }

    public float getVisib() {
        return visib;
    }

    public void setVisib(float visib) {
        this.visib = visib;
    }

    public float getWdsp() {
        return wdsp;
    }

    public void setWdsp(float wdsp) {
        this.wdsp = wdsp;
    }

    public float getPrcp() {
        return prcp;
    }

    public void setPrcp(float prcp) {
        this.prcp = prcp;
    }

    public float getSndp() {
        return sndp;
    }

    public void setSndp(float sndp) {
        this.sndp = sndp;
    }

    public byte getFrshtt() {
        return frshtt;
    }

    public void setFrshtt(byte frshtt) {
        this.frshtt = frshtt;
    }

    public float getCldc() {
        return cldc;
    }

    public void setCldc(float cldc) {
        this.cldc = cldc;
    }

    public short getWnddir() {
        return wnddir;
    }

    public void setWnddir(short wnddir) {
        this.wnddir = wnddir;
    }

}
