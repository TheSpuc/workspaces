package com.corejsf;

import java.io.Serializable;

import javax.inject.Named; 
import javax.enterprise.context.SessionScoped; 
import javax.faces.model.ArrayDataModel;
import javax.faces.model.DataModel;

@Named
@SessionScoped
public class TableData implements Serializable {
   private static final Name[] names = new Name[] {
       new Name("William", "Dupont"),
       new Name("Anna", "Keeney"),
       new Name("Mariko", "Randor"),
       new Name("John", "Wilson")
   };

   public Name[] getNames() { return names;}
}
