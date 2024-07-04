package lab07_03_30;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.HashMap;

public class Main {
	// class fields for managing options
	private static final String INPUT_OPT = "-i";
	private static final String OUTPUT_OPT = "-o";
	private static final String SORTED_OPT = "-sort";

	/*
	 * options with no argument are initially mapped to null, 
	 * when they are set they are mapped to a String array of length 0 
	 * options with one argument are mapped to a String array of length 1 initially containing null
	 */
	private static final Map<String, String[]> options = new HashMap<>();
	static {
		options.put(INPUT_OPT, new String[1]); // one argument, initially null
		options.put(OUTPUT_OPT, new String[1]); // one argument, initially null
		options.put(SORTED_OPT, null); // no arguments, option not set by default
	}

	// prints 'msg' on the standard error and exits with status code 1
	private static void error(String msg) {
		System.err.println(msg);
		System.exit(1);
	}

	// processes all options and their arguments, if any
	private static void processArgs(String[] args) {
		for (var i = 0; i < args.length; i++) {
			var opt = args[i];
			if (!options.containsKey(opt))
				error("Option error.\nValid options:\n\t-i <input>\n\t-o <output>\n\t-sort");
			var val = options.get(opt);
			if (val == null) // option with no argument 
				options.put(opt, new String[0]); // sets the option
			else if (val.length > 0) // option with one argument
			{
				if (i + 1 == args.length)
					error("Missing argument for option " + opt);
				val[0] = args[++i];
			}
		}
	}

	// writes a map into CSV format by using pw
	private static <K, V> void toCSV(PrintWriter pw, Map<K, V> map) {
		for (var k : map.entrySet())
			pw.println(k.toString().replace("=", ","));
	}

	// tries to open the input stream or the standard input if 'inputPath' is null
	// returns a corresponding buffered reader
	private static BufferedReader tryOpenInput(String inputPath) throws FileNotFoundException {
	    // da completare
		if(inputPath == null)
			return new BufferedReader(new InputStreamReader(System.in));
		return new BufferedReader(new FileReader(inputPath));
	}

	// tries to open the output stream or the standard output if 'outputPath' is null 
	// returns a corresponding print writer
	private static PrintWriter tryOpenOutput(String outputPath) throws FileNotFoundException {
	    // da completare
		if(outputPath == null)
			return new PrintWriter(System.out);
		return new PrintWriter(outputPath);
	}

	public static void main(String[] args) {
	    // processes the arguments
	    // manages streams and exceptions with try-with-resources
	    // calls 'merge' or 'mergeAndSort' methods and prints the result converted into CSV format
	    // da completare
		processArgs(args);
		Map<Person, Integer> map = null;
		try(var inputBuffer = tryOpenInput(options.get(INPUT_OPT)[0])) 
		{
			if(options.get(SORTED_OPT) == null) {
				CSVProcessor processor = new CSVProcessorUnsorted();
				map = processor.merge(inputBuffer);
			}
			else
			{
				CSVProcessor processor = new CSVProcessorSorted();
				map = processor.mergeAndSort(inputBuffer);
			}
		} catch (IOException e) {
			error(e.toString()); }
		try(var outputBuffer = tryOpenOutput(options.get(OUTPUT_OPT)[0]))
		{
			toCSV(outputBuffer, map);
		} catch(IOException e) {
			error(e.toString());	}
	}
}  