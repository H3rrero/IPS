package igu;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JTable;

import org.jvnet.substance.api.renderers.SubstanceDefaultTableCellRenderer;

public class RendererSubstance extends SubstanceDefaultTableCellRenderer{

	private static final long serialVersionUID = 1L;

		@Override
	    public Component getTableCellRendererComponent(JTable table, Object value,
	            boolean isSelected, boolean hasFocus, int row, int column) {
	
	       super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	       this.setFont(new Font("Dialog", Font.PLAIN, 14));
	       //table.getSelectedColumn()==6
	       if (column == 7)
		      {
	        	this.setForeground(Color.RED);
	        	this.setFont(new java.awt.Font("Dialog", Font.BOLD, 15));
		      }
	       if (column == 6)
		      {
	        	this.setForeground(Color.BLUE);
	        	this.setFont(new java.awt.Font("Dialog", Font.BOLD, 15));
		      }
	        return this;
	    }
		
		public Component getTableCellRendererComponent2(JTable table, Object value,
	            boolean isSelected, boolean hasFocus, int row, int column) {
	
	       super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	       this.setFont(new Font("Dialog", Font.PLAIN, 14));
	       if (column == 1)
		      {
	        	this.setForeground(Color.RED);
	        	this.setFont(new java.awt.Font("Dialog", Font.BOLD, 15));
		      }
	       if (column == 2)
		      {
	        	this.setForeground(Color.BLUE);
	        	this.setFont(new java.awt.Font("Dialog", Font.BOLD, 15));
		      }
	        return this;
	    }
	

}


