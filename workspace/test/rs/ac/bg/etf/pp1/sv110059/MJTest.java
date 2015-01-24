package rs.ac.bg.etf.pp1.sv110059;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import java_cup.runtime.Symbol;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

public class MJTest {

	//static {
		//DOMConfigurator.configure(Log4JUtils.instance().findLoggerConfigFile());
		//Log4JUtils.instance().prepareLogFile(Logger.getRootLogger());
	//}

	public static void main(String[] args) throws IOException {
		Logger log = Logger.getLogger(MJTest.class);
		log.setAdditivity(false);
		if (args.length >= 1) {
			FileAppender fa = new FileAppender();
			fa.setName("FileLogger");
			fa.setFile(args[0]);
			fa.setLayout(new PatternLayout("%d %-5p [%c{1}] %m%n"));
			fa.setThreshold(Level.DEBUG);
			fa.setAppend(false);
			fa.activateOptions();
			log.addAppender(fa);
		} else {
			ConsoleAppender console = new ConsoleAppender();
			String PATTERN = "%d [%p|%c|%C{1}] %m%n";
			console.setLayout(new PatternLayout(PATTERN));
			console.setThreshold(Level.DEBUG);
			console.activateOptions();
			log.addAppender(console);
		}
		Reader br = null;
		try {
			File sourceCode = new File("test/program.mj");
			log.info("Compiling source file: " + sourceCode.getAbsolutePath());
			br = new BufferedReader(new FileReader(sourceCode));
			Yylex lexer = new Yylex(br);
			Symbol currToken = null;
			while ((currToken = lexer.next_token()).sym != sym.EOF) {
				if (currToken != null)
					log.info(currToken.toString() + " "
							+ currToken.value.toString());
			}
		} finally {
			if (br != null)
				try {
					br.close();
				} catch (IOException e1) {
					log.error(e1.getMessage(), e1);
				}
		}
	}
}
