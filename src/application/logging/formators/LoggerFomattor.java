package application.logging.formators;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class LoggerFomattor extends Formatter {

	@Override
	public String format(LogRecord rec) {

		StringBuffer buf = new StringBuffer(1000);
		buf.append(new java.util.Date().toLocaleString());

		buf.append(" ");
		buf.append(rec.getLevel());
		buf.append("\t:\t");
		buf.append(formatMessage(rec));
		buf.append("\n");

		return buf.toString();

	}
}
