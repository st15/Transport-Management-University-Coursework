package com.trans.ui.tables;

import java.util.List;

import javax.swing.table.TableModel;

/**
 * @author Lili
 *
 */
public interface ITransportTableModel<T> extends TableModel {
	T getItemAt(int i);
	void reload(List<T> items);
}
