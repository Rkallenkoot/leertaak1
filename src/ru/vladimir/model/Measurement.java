package ru.vladimir.model;

import org.apache.commons.csv.CSVRecord;
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

    public Measurement() {

    }

    public Measurement(Element el) {
        setStn(el.getChildText("STN"));
        setDate(el.getChildText("DATE"));
        setTime(el.getChildText("TIME"));
        setTemp(el.getChildText("TEMP"));
        setDewp(el.getChildText("DEWP"));
        setStp(el.getChildText("STP"));
        setSlp(el.getChildText("SLP"));
        setVisib(el.getChildText("VISIB"));
        setWdsp(el.getChildText("WDSP"));
        setPrcp(el.getChildText("PRCP"));
        setSndp(el.getChildText("SNDP"));
        setFrshtt(el.getChildText("FRSHTT"));
        setCldc(el.getChildText("CLDC"));
        setWnddir(el.getChildText("WNDDIR"));
    }

    public Measurement(CSVRecord record) {
        setStn(record.get("stn"));
        setDate(record.get("date"));
        setTime(record.get("time"));
        setTemp(record.get("temp"));
        setDewp(record.get("dewp"));
        setStp(record.get("stp"));
        setSlp(record.get("slp"));
        setVisib(record.get("visib"));
        setWdsp(record.get("wdsp"));
        setPrcp(record.get("prcp"));
        setSndp(record.get("sndp"));
        setFrshtt(Short.valueOf(record.get("frshtt")));
        setCldc(record.get("cldc"));
        setWnddir(record.get("wnddir"));
    }


    public int getStn() {
        return stn;
    }

    public void setStn(int stn) {
        this.stn = stn;
    }

    public void setStn(String stn) {
        setStn(Integer.parseInt(stn));
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setDate(String date) {
        setDate(Date.valueOf(date));
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public void setTime(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
        long ms = 0;
        try {
            ms = sdf.parse(time).getTime();
        } catch (ParseException e) {
            // default to ms 0
            System.out.println("Parse Exception in Measurement: " + e.getMessage());
        }
        setTime(new Time(ms));
    }

    public float getTemp() {
        return temp;
    }

    public void setTemp(float temp) {
        this.temp = temp;
    }

    public void setTemp(String temp) {
        if (!temp.equals("")) {
            setTemp(Float.parseFloat(temp));
        } else {
            setTemp(Float.MAX_VALUE);
        }
    }

    public float getDewp() {
        return dewp;
    }

    public void setDewp(float dewp) {
        this.dewp = dewp;
    }

    public void setDewp(String dewp) {
        if (!dewp.equals("")) {
            setDewp(Float.parseFloat(dewp));
        } else {
            setDewp(Float.MAX_VALUE);
        }
    }

    public float getStp() {
        return stp;
    }

    public void setStp(float stp) {
        this.stp = stp;
    }

    public void setStp(String stp) {
        if (!stp.equals("")) {
            setStp(Float.parseFloat(stp));
        } else {
            setStp(Float.MAX_VALUE);
        }
    }

    public float getSlp() {
        return slp;
    }

    public void setSlp(float slp) {
        this.slp = slp;
    }

    public void setSlp(String slp) {
        if (!slp.equals("")) {
            setSlp(Float.parseFloat(slp));
        } else {
            setSlp(Float.MAX_VALUE);
        }
    }

    public float getVisib() {
        return visib;
    }

    public void setVisib(float visib) {
        this.visib = visib;
    }

    public void setVisib(String visib) {
        if (!visib.equals("")) {
            setVisib(Float.parseFloat(visib));
        } else {
            setVisib(Float.MAX_VALUE);
        }

    }

    public float getWdsp() {
        return wdsp;
    }

    public void setWdsp(float wdsp) {
        this.wdsp = wdsp;
    }

    public void setWdsp(String wdsp) {
        if (!wdsp.equals("")) {
            setWdsp(Float.parseFloat(wdsp));
        } else {
            setWdsp(Float.MAX_VALUE);
        }
    }

    public float getPrcp() {
        return prcp;
    }

    public void setPrcp(float prcp) {
        this.prcp = prcp;
    }

    public void setPrcp(String prcp) {
        if (!prcp.equals("")) {
            setPrcp(Float.parseFloat(prcp));
        } else {
            setPrcp(Float.MAX_VALUE);
        }
    }

    public float getSndp() {
        return sndp;
    }

    public void setSndp(float sndp) {
        this.sndp = sndp;
    }

    public void setSndp(String sndp) {
        if (!sndp.equals("")) {
            setSndp(Float.parseFloat(sndp));
        } else {
            setSndp(Float.MAX_VALUE);
        }
    }

    public byte getFrshtt() {
        return frshtt;
    }

    public void setFrshtt(String frshtt) {
        if (!frshtt.equals("")) {
            setFrshtt(Byte.valueOf(frshtt, 2));
        } else {
            setFrshtt(Byte.parseByte("0"));
        }
    }

    public void setFrshtt(short frshtt) {
        this.frshtt = (byte) frshtt;
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

    public void setCldc(String cldc) {
        if (!cldc.equals("")) {
            setCldc(Float.parseFloat(cldc));

        } else {
            setCldc(Float.MAX_VALUE);
        }
    }

    public short getWnddir() {
        return wnddir;
    }

    public void setWnddir(short wnddir) {
        this.wnddir = wnddir;
    }

    public void setWnddir(String wnddir) {
        if (!wnddir.equals("")) {
            setWnddir(Short.parseShort(wnddir));
        } else {
            setWnddir(Short.MAX_VALUE);
        }
    }

}
