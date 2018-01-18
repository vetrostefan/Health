package de.programmierenlernenhq.health;

/**
 * Created by Vetro on 08.01.2018.
 */

public class HealthMemo {

    private String vorname;
    private String nachname;
    private String gerät1;
    private String a1;
    private String b1;
    private String c1;
    private long id;


    public HealthMemo(String vorname, String nachname,String gerät1,String a1,String b1,String c1, long id) {
        this.vorname = vorname;
        this.nachname = nachname;
        this.gerät1 = gerät1;
        this.a1 = a1;
        this.b1 = b1;
        this.c1 = c1;
        this.id = id;
    }


    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }


    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public String getGerät1() {
        return gerät1;
    }

    public void setGerät1(String gerät1) {
        this.gerät1 = gerät1;
    }

    public String getA1() {
        return a1;
    }

    public void setA1(String a1) {
        this.a1 = a1;
    }

    public String getB1() {
        return b1;
    }

    public void setB1(String b1) {
        this.b1 = b1;
    }

    public String getC1() {
        return c1;
    }

    public void setC1(String c1) {
        this.c1 = c1;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    @Override
    public String toString() {
        String output = vorname + " " + nachname + " " +  "\n" + gerät1 + " A " + a1 + " B " + b1 + " C " + c1;

        return output;
    }
}
