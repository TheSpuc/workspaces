/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

package main;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mark
 */
public class IntegerList {
    
    private List<Integer> list;
    
    public IntegerList(){
        list = new ArrayList<>();
    }
    
    public List<Integer> getList(){
        return new ArrayList<>(list);
    }
    
    public void add(int i){
        list.add(i);
    }
    
    @Override
    public String toString(){
        String result = "";
        for(int i : list){
            result += i + ",";
        }
        return result;
    }
}
