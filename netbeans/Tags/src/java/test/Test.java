/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import javax.inject.Named;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;

@Named(value = "test")
@SessionScoped
public class Test implements Serializable {

    private String tekst1;
    private String tekst2;
    private boolean ok;

    /**
     * Creates a new instance of Test
     */
    public Test() {
    }

    public String getTekst1() {
        return tekst1;
    }

    public void setTekst1(String tekst1) {
        this.tekst1 = tekst1;
    }

    public String getTekst2() {
        return tekst2;
    }

    public void setTekst2(String tekst2) {
        this.tekst2 = tekst2;
    }

    public boolean isOk() {
        return ok;
    }

    public String changeOk() {
        ok = !ok;
        return null;
    }
}
