/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
 
@ManagedBean
public class GaleriaBean {
     
    private List<String> images1;
    private List<String> images2;
    private List<String> images3;
    private List<String> images4;
    @PostConstruct
    public void init() {
        images1 = new ArrayList<String>();
        images2 = new ArrayList<String>();
        images3 = new ArrayList<String>();
        images4 = new ArrayList<String>();
        for (int i = 1; i <= 3; i++) {
            images1.add("imagem" + i + ".jpg");
            images2.add("imagem"+(i+3)+".jpg");
            images3.add("imagem"+(i+6)+".jpg");
            images4.add("imagem"+(i+9)+".jpg");
        }
        
    }
 
    public List<String> getImages1() {
        return images1;
    }

    public List<String> getImages2() {
        return images2;
    }

    public List<String> getImages3() {
        return images3;
    }

    public List<String> getImages4() {
        return images4;
    }
    
}