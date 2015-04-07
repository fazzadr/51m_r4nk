package test;

import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import au.com.bytecode.opencsv.CSVWriter;
import sketch.KMinHashingSketch;
import sketch.KMinHashingSketchHandler;
import sketch.KMinHashingSketchSetContainment;
import sketch.KMinHashingSketchSetContainmentHandler;
import sketch.MinHashingSketch;
import sketch.MoreHashFunctionsMinHashSketchHandler;
import sketch.PermutedMinHashSketch;
import sketch.PermutedMinMaxHashSketch;
import tools.PermutationBiggestBasket;

public class IntersectionEstimation {

	protected static void test_KMinHashingSketchSetContainment() {
		// try to evaluate the intersection with this technique ;)
		int m = 4;
		int max_element = 10000;
		Set<Long> universe_set = new LinkedHashSet<Long>();
		Set<Long> set_5 = new LinkedHashSet<Long>();
		Set<Long> set_3 = new LinkedHashSet<Long>();
		Set<Long> set_2 = new LinkedHashSet<Long>();

		long[] array_to_permute = new long[max_element];

		//HashMap<Long, Long> fake_permutation = new HashMap<Long, Long>();
		for (long e = 1; e <= max_element; e++) {
			universe_set.add(e);
			array_to_permute[(int) e - 1] = e;
			if (e % 5 == 0) {
				set_5.add(e);
			}
			if (e % 3 == 0) {
				set_3.add(e);
			}
			if (e % 2 == 0) {
				set_2.add(e);
			}
		}

		PermutationBiggestBasket current_permutation = new PermutationBiggestBasket(array_to_permute);
		//HashMap<Long, Long> current_permutation = PermutedMinHashSketch.createAPermutationOfUniverseSetOfElements(universe_set);
		
		KMinHashingSketchSetContainment sk_5 = new KMinHashingSketchSetContainment(
				set_5, current_permutation, m, set_5.size());
		KMinHashingSketchSetContainment sk_3 = new KMinHashingSketchSetContainment(
				set_3, current_permutation, m, set_3.size());
		KMinHashingSketchSetContainment sk_2 = new KMinHashingSketchSetContainment(
				set_2, current_permutation, m, set_2.size());

		// KMinHashingSketchSetContainment[] sketches = new
		// KMinHashingSketchSetContainment[2];
		KMinHashingSketchSetContainment[] sketches = { sk_5, sk_3, sk_2 };
		KMinHashingSketchSetContainmentHandler k_set_containment_handler = new KMinHashingSketchSetContainmentHandler();
		double appx_containment = k_set_containment_handler.getMultipleContainmentSimilarity(sketches);

		System.out.println(" set_5.size()= " + set_5.size());
		System.out.println(" set_3.size()= " + set_3.size());
		System.out.println(" set_2.size()= " + set_2.size());
		Set<Long> intersection = new LinkedHashSet<Long>(set_5);
		intersection.retainAll(set_3);
		intersection.retainAll(set_2);
		double int_size = intersection.size();
		double containment = ((double) intersection.size() / (double) set_5
				.size());

		System.out.println();

		System.out.println(" sk_5.getLength()= " + sk_5.getLength());
		System.out.println(" sk_3.getLength()= " + sk_3.getLength());
		System.out.println(" sk_2.getLength()= " + sk_2.getLength());
		System.out.println(" sk_5 relative size= " + (float) 100
				* (float) sk_5.getLength() / (float) set_5.size() + "%");
		System.out.println(" sk_3 relative size= " + (float) 100
				* (float) sk_3.getLength() / (float) set_3.size() + "%");
		System.out.println(" sk_2 relative size= " + (float) 100
				* (float) sk_2.getLength() / (float) set_2.size() + "%");
		System.out.println();
		System.out.println();
		System.out.println(" containment(set_5, set_3, set_2)= " + containment);
		System.out.println(" appx_containment(set_5, set_3, set_2)= "
				+ appx_containment);
		System.out.println(" abs_error_in_containment= "
				+ Math.abs(appx_containment - containment));
		System.out.println(" rel_error_in_containment= " + (float) 100
				* Math.abs(appx_containment - containment) + "%");
		System.out.println();
		double appx_int = appx_containment * set_5.size();
		System.out.println(" |INT|= " + int_size);
		System.out.println(" appx_INT= " + appx_int);
		System.out.println(" abs_error_in_INT= " + (appx_int - int_size));
		System.out.println(" rel_error_in_INT= " + (float) 100
				* (appx_int - int_size) / (double) int_size + "%");
	}

	/**
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		//IntersectionEstimation.test_KMinHashingSketchSetContainment();
		//if (args.length > -1) return;

		
		//String output_common_path = "/home/simone/Scrivania"; 
		String output_common_path = "/Users/ikki/Dropbox/PHD/similarity_ranking/set_intersection/experiments/plots/2";
		
		String timeStamp = new SimpleDateFormat("yyyy_MM_dd__HH_mm_ss")
				.format(Calendar.getInstance().getTime());
		System.out.println(timeStamp);
		CSVWriter writer = new CSVWriter(new FileWriter(
				output_common_path + "/" + "results_" + timeStamp + ".csv"), ';');
		CSVWriter writer_2 = new CSVWriter(new FileWriter(
				output_common_path + "/" + "results_essential_" + timeStamp
						+ ".csv"), ';');
		
		 /*int max_samples = 100; 
		 int max_repetitions = 31; 
		 int sketches_size =400; 
		 int max_int_value = 100000; 
		 int intersection_size= 1000; 
		 int[] sets_sizes = { 10000, 9000, 8000, 7000, 6000, 5000, 4000, 3000};
		 //int max_int_value = 100000; 
		 //int [] sets_sizes = { 17000, 16000, 15000, 14000, 13000, 12000, 11000, 10000}; 
		 int bigger_cardinality = sets_sizes[0];*/
		

		int max_samples = 10;
		int max_repetitions = 7;
		int sketches_size = 50;
		int intersection_size = 100;
		int[] sets_sizes = { 1000, 900, 800, 700, 600, 500, 400, 300 };
		int max_int_value = 10000;
		int bigger_cardinality = sets_sizes[0];

		System.out.println();
		System.out.println(" sets_sizes= " + Arrays.toString(sets_sizes));

		String[] row = new String[5 + sets_sizes.length + 1 + 6 + 10];
		int i = 0;
		row[i++] = "sample";
		row[i++] = "sketches_size";
		row[i++] = "max_int_value";
		row[i++] = "intersection_size";
		row[i++] = "real jaccard";
		for (int c_set = 0; c_set < sets_sizes.length; c_set++) {
			row[i++] = "set_size";
		}
		row[i++] = "repetition";
		row[i++] = "appx |INT| minHash |union_set|";
		row[i++] = "appx |INT| minMaxHash |union_set|";
		row[i++] = "appx |INT| k_minHash";

		row[i++] = "appx |INT| minHash superset couples";
		row[i++] = "appx |INT| minHash |union_set| couples";
		row[i++] = "appx |INT| minMaxHash superset couples";
		row[i++] = "appx |INT| minMaxHash |union_set| couples";
		row[i++] = "appx |INT| k_minHash couples";

		row[i++] = "appx jacc minHash";
		row[i++] = "appx jacc minMaxHash";
		row[i++] = "appx jacc k_minHash";

		row[i++] = "appx jacc minHash superset";
		row[i++] = "appx jacc minHash";
		row[i++] = "appx jacc minMaxHash superset";
		row[i++] = "appx jacc minMaxHash";
		row[i++] = "appx jacc k_minHash";
		System.out.println(Arrays.toString(row));
		writer.writeNext(row);
		
		int number_of_statistics = 3;
		int number_of_methods = 9;		
		String[] row_2 = new String[5 + sets_sizes.length + 1 + number_of_statistics * number_of_methods];
		int j = 0;
		row_2[j++] = "sample";
		row_2[j++] = "sketches_size";
		row_2[j++] = "max_integer_value";
		row_2[j++] = "intersection_size";
		row_2[j++] = "real_jaccard";
		for (int c_set = 0; c_set < sets_sizes.length; c_set++) {
			row_2[j++] = "set_size";
		}
		row_2[j++] = "repetition";

		row_2[j++] = "median appx |INT| minHash |union_set|";
		row_2[j++] = "average appx |INT| minHash |union_set|";
		row_2[j++] = "medians average appx |INT| minHash |union_set|";
		row_2[j++] = "median appx |INT| minMaxHash |union_set|";
		row_2[j++] = "average appx |INT| minMaxHash |union_set|";
		row_2[j++] = "medians average appx |INT| minMaxHash |union_set|";
		row_2[j++] = "median appx |INT| minHash |union_set| couples";
		row_2[j++] = "average appx |INT| minHash |union_set| couples";
		row_2[j++] = "medians average appx |INT| minHash |union_set| couples";
		row_2[j++] = "median appx |INT| minMaxHash |union_set| couples";
		row_2[j++] = "average appx |INT| minMaxHash |union_set| couples";
		row_2[j++] = "medians average appx |INT| minMaxHash |union_set| couples";
		row_2[j++] = "median appx |INT| minHash superset couples";
		row_2[j++] = "average appx |INT| minHash superset couples";
		row_2[j++] = "medians average appx |INT| minHash superset couples";
		row_2[j++] = "median appx |INT| minMaxHash superset couples";
		row_2[j++] = "average appx |INT| minMaxHash superset couples";
		row_2[j++] = "medians average appx |INT| minMaxHash superset couples";
		row_2[j++] = "median appx |INT| k_minHash couples";
		row_2[j++] = "average appx |INT| k_minHash couples";
		row_2[j++] = "medians average appx |INT| k_minHash couples";
		row_2[j++] = "median appx |INT| k_minHash";
		row_2[j++] = "average appx |INT| k_minHash";
		row_2[j++] = "medians average appx |INT| k_minHash";
		row_2[j++] = "median appx |INT| k_minHash_set_containment";
		row_2[j++] = "average appx |INT| k_minHash_set_containment";
		row_2[j++] = "medians average appx |INT| k_minHash_set_containment";
		writer_2.writeNext(row_2);

		for (int sample = 1; sample <= max_samples; sample++) {
			List<Set<Long>> all_sets = createDatasetForIntersectionEstimationTest(
					max_int_value, intersection_size, sets_sizes);

			Set<Long> union_set = create_union_set(all_sets);
			// System.out.println(union_set);
			// System.out.println();
			// System.out.println(" union cardinality= " + union_set.size());
			// System.out.println(" intersection cardinality= " +
			// get_intersection_cardinality(all_sets, union_set));
			// System.out.println(" precise Jaccard= " + (double)(
			// get_intersection_cardinality(all_sets, union_set) ) / (double)(
			// union_set.size() ) );
			// System.out.println();

			// System.out.println("   APPX |INT|= " +
			// (double)k_minhash_sketches[0].getMultipleJaccardSimilarity(k_minhash_sketches)*(double)union_set.size()
			// );

			/*long[] universe_set_array = new long[universe_set.size()];
			int universe_set_array_index=0;
			for(Long l: universe_set){
				universe_set_array[universe_set_array_index] = l;
				universe_set_array_index +=1;
			}*/
			long[] universe_set_array = new long[max_int_value];
			for(int index=0; index<max_int_value; index++){
				universe_set_array[index]=index+1;
			}
		
			LinkedHashMap<String, ArrayList<Integer>> set_int_appxs = new LinkedHashMap<String, ArrayList<Integer>>();
			set_int_appxs.put("appx |INT| minHash |union_set|",
					new ArrayList<Integer>());
			set_int_appxs.put("appx |INT| minMaxHash |union_set|",
					new ArrayList<Integer>());
			set_int_appxs.put("appx |INT| minHash |union_set| couples",
					new ArrayList<Integer>());
			set_int_appxs.put("appx |INT| minMaxHash |union_set| couples",
					new ArrayList<Integer>());
			set_int_appxs.put("appx |INT| minHash superset couples",
					new ArrayList<Integer>());
			set_int_appxs.put("appx |INT| minMaxHash superset couples",
					new ArrayList<Integer>());
			set_int_appxs.put("appx |INT| k_minHash couples",
					new ArrayList<Integer>());
			set_int_appxs.put("appx |INT| k_minHash", new ArrayList<Integer>());
			set_int_appxs.put("appx |INT| k_minHash_set_containment", new ArrayList<Integer>());
			
			LinkedHashMap<String, Long> last_medians = new LinkedHashMap<String, Long>();
			last_medians.put("appx |INT| minHash |union_set|", (long) 0);
			last_medians.put("appx |INT| minMaxHash |union_set|", (long) 0);
			last_medians.put("appx |INT| minHash |union_set| couples", (long) 0);
			last_medians.put("appx |INT| minMaxHash |union_set| couples", (long) 0);
			last_medians.put("appx |INT| minHash superset couples", (long) 0);
			last_medians.put("appx |INT| minMaxHash superset couples", (long) 0);
			last_medians.put("appx |INT| k_minHash couples", (long) 0);
			last_medians.put("appx |INT| k_minHash", (long) 0);
			last_medians.put("appx |INT| k_minHash_set_containment", (long) 0);
			
			for (int repetition = 1; repetition <= max_repetitions; repetition++) {
				System.out.println("sample/repetition " + sample + " "
						+ repetition);
				LinkedHashMap<String, Double> set_int_appx = IntersectionEstimation
						.workOnSinglesSets(all_sets, sketches_size,
								universe_set_array, union_set, bigger_cardinality);
				LinkedHashMap<String, Double> set_int_appx_with_couples = IntersectionEstimation
						.workOnSinglesIntersectionsOfCouplesOfSets(all_sets,
								sketches_size, universe_set_array);

				set_int_appxs.get("appx |INT| minHash |union_set|").add(
						(int) Math.ceil(set_int_appx
								.get("appx |INT| minHash |union_set|")));
				set_int_appxs.get("appx |INT| minMaxHash |union_set|").add(
						(int) Math.ceil(set_int_appx
								.get("appx |INT| minMaxHash |union_set|")));
				set_int_appxs.get("appx |INT| minHash |union_set| couples")
						.add((int) Math.ceil(set_int_appx_with_couples
								.get("appx |INT| minHash |union_set|")));
				set_int_appxs.get("appx |INT| minMaxHash |union_set| couples")
						.add((int) Math.ceil(set_int_appx_with_couples
								.get("appx |INT| minMaxHash |union_set|")));
				set_int_appxs.get("appx |INT| minHash superset couples").add(
						(int) Math.ceil(set_int_appx_with_couples
								.get("appx |INT| minHash superset")));
				set_int_appxs.get("appx |INT| minMaxHash superset couples")
						.add((int) Math.ceil(set_int_appx_with_couples
								.get("appx |INT| minMaxHash superset")));
				set_int_appxs.get("appx |INT| k_minHash couples").add(
						(int) Math.ceil(set_int_appx_with_couples
								.get("appx |INT| k_minHash")));
				set_int_appxs.get("appx |INT| k_minHash").add(
						(int) Math.ceil(set_int_appx
								.get("appx |INT| k_minHash")));
				set_int_appxs.get("appx |INT| k_minHash_set_containment").add(
						(int) Math.ceil(set_int_appx
								.get("appx |INT| k_minHash_set_containment")));
				
				j = 0;
				row_2[j++] = "" + sample;
				row_2[j++] = "" + sketches_size;
				row_2[j++] = "" + max_int_value;
				row_2[j++] = "" + intersection_size;
				row_2[j++] = ""
						+ ((double) (get_intersection_cardinality(all_sets,
								union_set)) / (double) (union_set.size()));
				for (int c_size : sets_sizes) {
					row_2[j++] = "" + c_size;
				}
				row_2[j++] = "" + repetition;
				if (repetition%2 == 1){
					Long new_value = (long) IntersectionEstimation.getMedian(set_int_appxs
							.get("appx |INT| minHash |union_set|"));
					row_2[j++] = ""
							+ new_value;
					last_medians.put("appx |INT| minHash |union_set|", new_value);
				}else{
					row_2[j++] = ""
							+ last_medians.get("appx |INT| minHash |union_set|");
				}
				row_2[j++] = ""
						+ IntersectionEstimation.getAverage(set_int_appxs
								.get("appx |INT| minHash |union_set|"));
				row_2[j++] = ""
						+ IntersectionEstimation
								.getAverageMedian(set_int_appxs
										.get("appx |INT| minHash |union_set|"));
				
				if (repetition%2 == 1){
					Long new_value = (long) IntersectionEstimation.getMedian(set_int_appxs
							.get("appx |INT| minMaxHash |union_set|"));
					row_2[j++] = ""
							+ new_value;
					last_medians.put("appx |INT| minMaxHash |union_set|", new_value);
				}else{
					row_2[j++] = ""
							+ last_medians.get("appx |INT| minMaxHash |union_set|");
				}
				row_2[j++] = ""
						+ IntersectionEstimation.getAverage(set_int_appxs
								.get("appx |INT| minMaxHash |union_set|"));
				row_2[j++] = ""
						+ IntersectionEstimation
								.getAverageMedian(set_int_appxs
										.get("appx |INT| minMaxHash |union_set|"));
				
				if (repetition%2 == 1){
					Long new_value = (long) IntersectionEstimation.getMedian(set_int_appxs
							.get("appx |INT| minHash |union_set| couples"));
					row_2[j++] = ""
							+ new_value;
					last_medians.put("appx |INT| minHash |union_set| couples", new_value);
				}else{
					row_2[j++] = ""
							+ last_medians.get("appx |INT| minHash |union_set| couples");
				}
				row_2[j++] = ""
						+ IntersectionEstimation
								.getAverage(set_int_appxs
										.get("appx |INT| minHash |union_set| couples"));
				row_2[j++] = ""
						+ IntersectionEstimation
								.getAverageMedian(set_int_appxs
										.get("appx |INT| minHash |union_set| couples"));
				
				if (repetition%2 == 1){
					Long new_value = (long) IntersectionEstimation.getMedian(set_int_appxs
							.get("appx |INT| minMaxHash |union_set| couples"));
					row_2[j++] = ""
							+ new_value;
					last_medians.put("appx |INT| minMaxHash |union_set| couples", new_value);
				}else{
					row_2[j++] = ""
							+ last_medians.get("appx |INT| minMaxHash |union_set| couples");
				}
				row_2[j++] = ""
						+ IntersectionEstimation
								.getAverage(set_int_appxs
										.get("appx |INT| minMaxHash |union_set| couples"));
				row_2[j++] = ""
						+ IntersectionEstimation
								.getAverageMedian(set_int_appxs
										.get("appx |INT| minMaxHash |union_set| couples"));
				
				if (repetition%2 == 1){
					Long new_value = (long) IntersectionEstimation.getMedian(set_int_appxs
							.get("appx |INT| minHash superset couples"));
					row_2[j++] = ""
							+ new_value;
					last_medians.put("appx |INT| minHash superset couples", new_value);
				}else{
					row_2[j++] = ""
							+ last_medians.get("appx |INT| minHash superset couples");
				}
				row_2[j++] = ""
						+ IntersectionEstimation
								.getAverage(set_int_appxs
										.get("appx |INT| minHash superset couples"));
				row_2[j++] = ""
						+ IntersectionEstimation
								.getAverageMedian(set_int_appxs
										.get("appx |INT| minHash superset couples"));
				
				if (repetition%2 == 1){
					Long new_value = (long) IntersectionEstimation.getMedian(set_int_appxs
							.get("appx |INT| minMaxHash superset couples"));
					row_2[j++] = ""
							+ new_value;
					last_medians.put("appx |INT| minMaxHash superset couples", new_value);
				}else{
					row_2[j++] = ""
							+ last_medians.get("appx |INT| minMaxHash superset couples");
				}
				row_2[j++] = ""
						+ IntersectionEstimation
								.getAverage(set_int_appxs
										.get("appx |INT| minMaxHash superset couples"));
				row_2[j++] = ""
						+ IntersectionEstimation
								.getAverageMedian(set_int_appxs
										.get("appx |INT| minMaxHash superset couples"));
				
				if (repetition%2 == 1){
					Long new_value = (long) IntersectionEstimation.getMedian(set_int_appxs
							.get("appx |INT| k_minHash couples"));
					row_2[j++] = ""
							+ new_value;
					last_medians.put("appx |INT| k_minHash couples", new_value);
				}else{
					row_2[j++] = ""
							+ last_medians.get("appx |INT| k_minHash couples");
				}
				row_2[j++] = ""
						+ IntersectionEstimation.getAverage(set_int_appxs
								.get("appx |INT| k_minHash couples"));
				row_2[j++] = ""
						+ IntersectionEstimation
								.getAverageMedian(set_int_appxs
										.get("appx |INT| k_minHash couples"));
				
				if (repetition%2 == 1){
					Long new_value = (long) IntersectionEstimation.getMedian(set_int_appxs
							.get("appx |INT| k_minHash"));
					row_2[j++] = ""
							+ new_value;
					last_medians.put("appx |INT| k_minHash", new_value);
				}else{
					row_2[j++] = ""
							+ last_medians.get("appx |INT| k_minHash");
				}
				row_2[j++] = ""
						+ IntersectionEstimation.getAverage(set_int_appxs
								.get("appx |INT| k_minHash"));
				row_2[j++] = ""
						+ IntersectionEstimation
								.getAverageMedian(set_int_appxs
										.get("appx |INT| k_minHash"));
				
				if (repetition%2 == 1){
					Long new_value = (long) IntersectionEstimation.getMedian(set_int_appxs
							.get("appx |INT| k_minHash_set_containment"));
					row_2[j++] = ""
							+ new_value;
					last_medians.put("appx |INT| k_minHash_set_containment", new_value);
				}else{
					row_2[j++] = ""
							+ last_medians.get("appx |INT| k_minHash_set_containment");
				}
				row_2[j++] = ""
						+ IntersectionEstimation.getAverage(set_int_appxs
								.get("appx |INT| k_minHash_set_containment"));
				row_2[j++] = ""
						+ IntersectionEstimation
								.getAverageMedian(set_int_appxs
										.get("appx |INT| k_minHash_set_containment"));
				
				writer_2.writeNext(row_2);
				writer_2.flush();
				
				// for (Map.Entry<String, Double> measure_value:
				// set_int_appx.entrySet()) {
				// System.out.println(measure_value);
				// }
				// System.out.println();
				// System.out.println("set_int_appx_with_couples");
				// for (Map.Entry<String, Double> measure_value:
				// set_int_appx_with_couples.entrySet()) {
				// System.out.println(measure_value);
				// }

				i = 0;
				row[i++] = "" + sample;
				row[i++] = "" + sketches_size;
				row[i++] = "" + max_int_value;
				row[i++] = "" + intersection_size;
				row[i++] = ""
						+ ((double) (get_intersection_cardinality(all_sets,
								union_set)) / (double) (union_set.size()));
				for (int c_size : sets_sizes) {
					row[i++] = "" + c_size;
				}
				row[i++] = "" + repetition;
				row[i++] = ""
						+ (int) Math.ceil(set_int_appx
								.get("appx |INT| minHash |union_set|"));
				row[i++] = ""
						+ (int) Math.ceil(set_int_appx
								.get("appx |INT| minMaxHash |union_set|"));
				row[i++] = ""
						+ (int) Math.ceil(set_int_appx
								.get("appx |INT| k_minHash"));

				row[i++] = ""
						+ (int) Math.ceil(set_int_appx_with_couples
								.get("appx |INT| minHash superset"));
				row[i++] = ""
						+ (int) Math.ceil(set_int_appx_with_couples
								.get("appx |INT| minHash |union_set|"));
				row[i++] = ""
						+ (int) Math.ceil(set_int_appx_with_couples
								.get("appx |INT| minMaxHash superset"));
				row[i++] = ""
						+ (int) Math.ceil(set_int_appx_with_couples
								.get("appx |INT| minMaxHash |union_set|"));
				row[i++] = ""
						+ (int) Math.ceil(set_int_appx_with_couples
								.get("appx |INT| k_minHash"));

				row[i++] = "" + set_int_appx.get("appx jacc minHash");
				row[i++] = "" + set_int_appx.get("appx jacc minMaxHash");
				row[i++] = "" + set_int_appx.get("appx jacc k_minHash");

				row[i++] = ""
						+ set_int_appx_with_couples
								.get("appx jacc minHash superset");
				row[i++] = ""
						+ set_int_appx_with_couples.get("appx jacc minHash");
				row[i++] = ""
						+ set_int_appx_with_couples
								.get("appx jacc minMaxHash superset");
				row[i++] = ""
						+ set_int_appx_with_couples.get("appx jacc minMaxHash");
				row[i++] = ""
						+ set_int_appx_with_couples.get("appx jacc k_minHash");

				writer.writeNext(row);
				System.out.println(Arrays.toString(row));
				writer.flush();
			}

		}
		writer.close();
		writer_2.close();

	}

	protected static double getAverage(ArrayList<Integer> values) {
		double total = 0;
		for (int i = 0; i < values.size(); i++) {
			total += values.get(i);
		}

		return total / values.size();
	}

	protected static int getMedian(ArrayList<Integer> values) {
		Collections.sort(values);
		return values.get(values.size() / 2);
	}

	protected static double getAverageMedian(ArrayList<Integer> values) {
		if(values.size()%2 == 1){
			return getMedian(values);
		}
		Collections.sort(values);
		double avg = (double) values.get(values.size() / 2)
				+ values.get(values.size() / 2 - 1);
		avg = avg / 2;
		return avg;

	}

	protected static LinkedHashMap<String, Double> workOnSinglesSets(
			List<Set<Long>> all_sets, int sketches_size,
			long[] universe_set, Set<Long> union_set, int bigger_cardinality) {

		LinkedHashMap<String, Double> measure_value = new LinkedHashMap<String, Double>();

		long[][] raw_minhash_sketches = new long[all_sets.size()][sketches_size];
		long[][] raw_minMaxhash_sketches = new long[all_sets.size()][sketches_size];
		
		KMinHashingSketch[] k_minhash_sketches = new KMinHashingSketch[all_sets
				.size()];
		KMinHashingSketchSetContainment[] k_minhash_sketches_set_containment = new KMinHashingSketchSetContainment[all_sets.size()];
		
		long[] min_max = { 0, 0 };
		PermutationBiggestBasket current_permutation; 
		//HashMap<Long, Long> current_permutation;
		for (int perm = 0; perm < sketches_size; perm++) {
			current_permutation = new PermutationBiggestBasket(universe_set);
			current_permutation.permute();
			//current_permutation = PermutedMinHashSketch.createAPermutationOfUniverseSetOfElements(universe_set);
			for (int set_index = 0; set_index < all_sets.size(); set_index++) {
				min_max = PermutedMinMaxHashSketch.getMinMaxHashValue(
						all_sets.get(set_index), current_permutation);
				raw_minhash_sketches[set_index][perm] = min_max[0];
				if (perm < sketches_size / 2) {
					// min_max =
					// PermutedMinMaxHashSketch.getMinMaxHashValue(all_sets.get(set_index),
					// current_permutation);
					raw_minMaxhash_sketches[set_index][perm * 2] = min_max[0];
					raw_minMaxhash_sketches[set_index][perm * 2 + 1] = min_max[1];
				}
				if (perm == 0) {
					k_minhash_sketches[set_index] = new KMinHashingSketch(
							all_sets.get(set_index), current_permutation,
							sketches_size);
					
					k_minhash_sketches_set_containment[set_index] = new KMinHashingSketchSetContainment(all_sets.get(set_index), current_permutation, 32, all_sets.get(set_index).size());
				}
			}
		}
		
		
		
		MoreHashFunctionsMinHashSketchHandler more_minhash_handler = new MoreHashFunctionsMinHashSketchHandler();
		KMinHashingSketchHandler k_minhash_handler = new KMinHashingSketchHandler();
		KMinHashingSketchSetContainmentHandler k_minhash_set_containment_handler = new KMinHashingSketchSetContainmentHandler();		
		// System.out.println();

		MinHashingSketch[] minhash_sketches = new MinHashingSketch[all_sets
				.size()];
		for (int perm = 0; perm < raw_minhash_sketches.length; perm++) {
			minhash_sketches[perm] = new PermutedMinHashSketch(
					raw_minhash_sketches[perm]);
			// System.out.println(minhash_sketches[perm]);
		}
		// System.out.println(" APPX jaccard= " +
		// minhash_sketches[0].getMultipleJaccardSimilarity(minhash_sketches));
		// System.out.println(" APPX |INT|=  " +
		// (double)minhash_sketches[0].getMultipleJaccardSimilarity(minhash_sketches)
		// * (double)union_set.size());
		measure_value.put("appx jacc minHash", more_minhash_handler
				.getMultipleJaccardSimilarity(minhash_sketches));
		measure_value.put(
				"appx |INT| minHash |union_set|",
				(double) more_minhash_handler
						.getMultipleJaccardSimilarity(minhash_sketches)
						* (double) union_set.size());

		MinHashingSketch[] minMaxhash_sketches = new MinHashingSketch[all_sets
				.size()];
		for (int perm = 0; perm < raw_minhash_sketches.length; perm++) {
			minMaxhash_sketches[perm] = new PermutedMinMaxHashSketch(
					raw_minMaxhash_sketches[perm]);
			// System.out.println(minMaxhash_sketches[perm]);
		}
		// System.out.println(" APPX jaccard= " +
		// minMaxhash_sketches[0].getMultipleJaccardSimilarity(minMaxhash_sketches));
		// System.out.println(" APPX |INT|=  " +
		// (double)minMaxhash_sketches[0].getMultipleJaccardSimilarity(minMaxhash_sketches)
		// * (double)union_set.size());
		measure_value.put("appx jacc minMaxHash", more_minhash_handler
				.getMultipleJaccardSimilarity(minMaxhash_sketches));
		measure_value.put(
				"appx |INT| minMaxHash |union_set|",
				(double) more_minhash_handler
						.getMultipleJaccardSimilarity(minMaxhash_sketches)
						* (double) union_set.size());

		// for (int perm = 0; perm < raw_minhash_sketches.length; perm++) {
		// System.out.println(k_minhash_sketches[perm]);
		// }
		// System.out.println(" APPX jaccard= " +
		// k_minhash_sketches[0].getMultipleJaccardSimilarity(k_minhash_sketches));
		// System.out.println(" APPX |INT|= " +
		// (double)k_minhash_sketches[0].getMultipleJaccardSimilarity(k_minhash_sketches)*(double)bigger_cardinality);
		measure_value.put("appx jacc k_minHash", k_minhash_handler
				.getMultipleJaccardSimilarity(k_minhash_sketches));
		measure_value.put(
				"appx |INT| k_minHash",
				(double) k_minhash_handler
						.getMultipleJaccardSimilarity(k_minhash_sketches)
						* (double) bigger_cardinality);
		/*
		measure_value.put("appx |INT| k_minHash_set_containment", 
				(double) k_minhash_set_containment_handler
				.getMultipleContainmentSimilarity(k_minhash_sketches_set_containment)
				* (double) k_minhash_sketches_set_containment[0].getLength());
		*/
		double int_from_containment =  k_minhash_set_containment_handler
				.getIntersectionCardinality(k_minhash_sketches_set_containment);
		measure_value.put("appx |INT| k_minHash_set_containment", int_from_containment);
		return measure_value;
	}

	/**
	 * 
	 * @param all_sets
	 * @param sketches_size
	 * @param universe_set
	 */
	protected static LinkedHashMap<String, Double> workOnSinglesIntersectionsOfCouplesOfSets(
			List<Set<Long>> all_sets, int sketches_size, long[] universe_set) {

		LinkedHashMap<String, Double> measure_value = new LinkedHashMap<String, Double>();

		Set<Long> smaller_set = null;
		int smaller_set_index = 0;
		int smaller_set_size = Integer.MAX_VALUE;
		for (int i = 0; i < all_sets.size(); i++) {
			Set<Long> current_set = all_sets.get(i);
			if (current_set.size() < smaller_set_size) {
				smaller_set = current_set;
				smaller_set_size = smaller_set.size();
				smaller_set_index = i;
			}
		}

		MoreHashFunctionsMinHashSketchHandler more_minhash_handler = new MoreHashFunctionsMinHashSketchHandler();
		KMinHashingSketchHandler k_minhash_handler = new KMinHashingSketchHandler();
		KMinHashingSketchSetContainmentHandler k_minhash_set_containment_handler = new KMinHashingSketchSetContainmentHandler();
		
		long[][] raw_minhash_sketches = new long[all_sets.size()][sketches_size];
		long[][] raw_minMaxhash_sketches = new long[all_sets.size()][sketches_size];
		List<KMinHashingSketch> k_minhash_sketches = new ArrayList<KMinHashingSketch>(
				all_sets.size() - 1);
		long[] min_max = { 0, 0 };
		PermutationBiggestBasket current_permutation;
		//HashMap<Long, Long> current_permutation;
		Set<Long> intersecion_set = null;
		Set<Long> union_set = new LinkedHashSet<Long>();
		int bigger_cardinality = 0;
		for (int perm = 0; perm < sketches_size; perm++) {
			current_permutation = new PermutationBiggestBasket(universe_set);

			for (int set_index = 0; set_index < all_sets.size(); set_index++) {

				intersecion_set = (set_index == smaller_set_index ? smaller_set
						: IntersectionEstimation.getIntersectionSet(
								smaller_set, all_sets.get(set_index)));

				if (intersecion_set.size() <= sketches_size) {
					System.out.println("intersecion_set.size(): "
							+ intersecion_set.size());
				}

				if (set_index != smaller_set_index) {
					union_set.addAll(intersecion_set);
					if (intersecion_set.size() > bigger_cardinality) {
						bigger_cardinality = intersecion_set.size();
					}
					if (perm == 0) {
						k_minhash_sketches.add(new KMinHashingSketch(
								intersecion_set, current_permutation,
								sketches_size));
					}
				}

				min_max = PermutedMinMaxHashSketch.getMinMaxHashValue(
						intersecion_set, current_permutation);
				raw_minhash_sketches[set_index][perm] = min_max[0];

				if (perm < sketches_size / 2) {
					raw_minMaxhash_sketches[set_index][perm * 2] = min_max[0];
					raw_minMaxhash_sketches[set_index][perm * 2 + 1] = min_max[1];
				}
			}
		}

		// System.out.println();

		MinHashingSketch[] minhash_sketches = new MinHashingSketch[raw_minhash_sketches.length];
		for (int perm = 0; perm < raw_minhash_sketches.length; perm++) {
			minhash_sketches[perm] = new PermutedMinHashSketch(
					raw_minhash_sketches[perm]);
			// System.out.println(minhash_sketches[perm]);
		}
		// System.out.println(" APPX jaccard= " +
		// minhash_sketches[0].getMultipleJaccardSimilarity(minhash_sketches));
		// System.out.println(" APPX |INT|=  " +
		// (double)minhash_sketches[0].getMultipleJaccardSimilarity(minhash_sketches)
		// * (double)smaller_set_size);
		measure_value.put("appx jacc minHash superset", more_minhash_handler
				.getMultipleJaccardSimilarity(minhash_sketches));
		measure_value.put(
				"appx |INT| minHash superset",
				(double) more_minhash_handler
						.getMultipleJaccardSimilarity(minhash_sketches)
						* (double) smaller_set_size);
		MinHashingSketch[] minhash_sketches_without_superset = new MinHashingSketch[raw_minhash_sketches.length - 1];
		int second_index = 0;
		for (int index = 0; index < minhash_sketches.length; index++) {
			if (index == smaller_set_index) {
				continue;
			}
			minhash_sketches_without_superset[second_index] = minhash_sketches[index];
			second_index++;
		}
		// System.out.println(" APPX jaccard= " +
		// minhash_sketches_without_superset[0].getMultipleJaccardSimilarity(minhash_sketches_without_superset));
		// System.out.println(" APPX |INT|=  " +
		// (double)minhash_sketches_without_superset[0].getMultipleJaccardSimilarity(minhash_sketches_without_superset)
		// * (double)union_set.size());
		measure_value
				.put("appx jacc minHash",
						more_minhash_handler
								.getMultipleJaccardSimilarity(minhash_sketches_without_superset));
		measure_value
				.put("appx |INT| minHash |union_set|",
						(double) more_minhash_handler
								.getMultipleJaccardSimilarity(minhash_sketches_without_superset)
								* (double) union_set.size());

		MinHashingSketch[] minMaxhash_sketches = new MinHashingSketch[raw_minMaxhash_sketches.length];
		for (int perm = 0; perm < minMaxhash_sketches.length; perm++) {
			minMaxhash_sketches[perm] = new PermutedMinMaxHashSketch(
					raw_minMaxhash_sketches[perm]);
			// System.out.println(minMaxhash_sketches[perm]);
		}
		// System.out.println(" APPX jaccard= " +
		// minMaxhash_sketches[0].getMultipleJaccardSimilarity(minMaxhash_sketches));
		// System.out.println(" APPX |INT|=  " +
		// (double)minMaxhash_sketches[0].getMultipleJaccardSimilarity(minMaxhash_sketches)
		// * (double)smaller_set_size);
		measure_value.put("appx jacc minMaxHash superset",
				more_minhash_handler
						.getMultipleJaccardSimilarity(minMaxhash_sketches));
		measure_value.put(
				"appx |INT| minMaxHash superset",
				(double) more_minhash_handler
						.getMultipleJaccardSimilarity(minMaxhash_sketches)
						* (double) smaller_set_size);
		MinHashingSketch[] minmaxhash_sketches_without_superset = new MinHashingSketch[raw_minhash_sketches.length - 1];
		second_index = 0;
		for (int index = 0; index < minMaxhash_sketches.length; index++) {
			if (index == smaller_set_index) {
				continue;
			}
			minmaxhash_sketches_without_superset[second_index] = minMaxhash_sketches[index];
			second_index++;
		}
		measure_value
				.put("appx jacc minMaxHash",
						more_minhash_handler
								.getMultipleJaccardSimilarity(minmaxhash_sketches_without_superset));
		measure_value
				.put("appx |INT| minMaxHash |union_set|",
						(double) more_minhash_handler
								.getMultipleJaccardSimilarity(minmaxhash_sketches_without_superset)
								* (double) union_set.size());

		// for (KMinHashingSketch k_minhash_sketch : k_minhash_sketches) {
		// System.out.println(k_minhash_sketch);
		// }
		KMinHashingSketch[] k_minhash_sketches_array = null;
		k_minhash_sketches_array = k_minhash_sketches
				.toArray(new KMinHashingSketch[0]);
		// System.out.println(" APPX jaccard= " +
		// k_minhash_sketches_array[0].getMultipleJaccardSimilarity(k_minhash_sketches_array));
		// System.out.println(" APPX |INT|= " +
		// (double)k_minhash_sketches_array[0].getMultipleJaccardSimilarity(k_minhash_sketches_array)*(double)bigger_cardinality);
		measure_value.put("appx jacc k_minHash", k_minhash_handler
				.getMultipleJaccardSimilarity(k_minhash_sketches_array));
		measure_value.put(
				"appx |INT| k_minHash",
				(double) k_minhash_handler
						.getMultipleJaccardSimilarity(k_minhash_sketches_array)
						* (double) bigger_cardinality);

		return measure_value;
	}

	protected static Set<Long> getIntersectionSet(Set<Long> set_1,
			Set<Long> set_2) {
		Set<Long> int_set = new LinkedHashSet<Long>();
		Set<Long> smaller_set = set_1;
		Set<Long> bigger_set = set_2;
		if (set_2.size() < smaller_set.size()) {
			smaller_set = set_2;
			bigger_set = set_1;
		}
		for (Long element : smaller_set) {
			if (bigger_set.contains(element)) {
				int_set.add(element);
			}
		}
		return int_set;
	}

	protected static int get_intersection_cardinality(List<Set<Long>> all_sets,
			Set<Long> union_set) {
		int intersection_cardinality = 0;
		boolean is_contained_in_all_sets = true;
		for (Long element : union_set) {
			is_contained_in_all_sets = true;
			for (Set<Long> c_set : all_sets) {
				if (!c_set.contains(element)) {
					is_contained_in_all_sets = false;
				}
			}
			if (is_contained_in_all_sets) {
				intersection_cardinality++;
			}
		}
		return intersection_cardinality;
	}

	protected static Set<Long> create_union_set(List<Set<Long>> all_sets) {
		Set<Long> union_set = new TreeSet<Long>();
		for (Set<Long> c_set : all_sets) {
			union_set.addAll(c_set);
		}
		return union_set;
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	protected static ArrayList<Set<Long>> createDatasetForIntersectionEstimationTest(
			int max_int_value, int intersection_size, int[] sets_sizes)
			throws Exception {
		ArrayList<Set<Long>> all_sets = new ArrayList<Set<Long>>();
		int i = 0;
		for (i = 0; i < sets_sizes.length; i++) {
			all_sets.add(new TreeSet<Long>());
		}
		Random r = new Random();
		long new_element = 0;
		Set<Long> intersection_set = new TreeSet<Long>();
		i = 0;
		while (all_sets.get(0).size() < intersection_size) {
			new_element = (long) (r.nextInt(max_int_value - 1) + 1);
			for (Set<Long> c_set : all_sets) {
				c_set.add(new_element);
				intersection_set.add(new_element);
			}
		}
		boolean is_new_element_a_real_new_element = true;
		int number_of_sets_with_this_new_element = 0;
		boolean[] insert_array = new boolean[sets_sizes.length];
		for (i = 0; i < insert_array.length; i++) {
			insert_array[i] = false;
		}
		while (!are_all_sets_full(all_sets, sets_sizes)) {
			// System.out.println();
			new_element = r.nextInt(max_int_value - 1) + 1;
			// System.out.println("new_element= " + new_element);
			is_new_element_a_real_new_element = true;
			for (Set<Long> c_set : all_sets) {
				if (c_set.contains(new_element)) {
					is_new_element_a_real_new_element = false;
					break;
				}
			}
			if (!is_new_element_a_real_new_element) {
				continue;
			}
			i = 0;
			number_of_sets_with_this_new_element = 0;
			for (i = 0; i < insert_array.length; i++) {
				insert_array[i] = r.nextBoolean();
				if (insert_array[i]) {
					number_of_sets_with_this_new_element++;
				}
			}
			// System.out.println("insert_array= " +
			// Arrays.toString(insert_array));
			// System.out.println("number_of_sets_with_this_new_element= " +
			// number_of_sets_with_this_new_element);
			if (number_of_sets_with_this_new_element == sets_sizes.length) {
				insert_array[r.nextInt(insert_array.length)] = false;
			}
			if (number_of_sets_with_this_new_element == 0) {
				insert_array[r.nextInt(insert_array.length)] = true;
			}
			i = 0;
			for (Set<Long> c_set : all_sets) {
				if (insert_array[i]) {
					if (c_set.size() < sets_sizes[i]) {
						c_set.add(new_element);
					}
				}
				i++;
			}
		}
		return all_sets;
	}

	protected static boolean are_all_sets_full(List<Set<Long>> all_sets,
			int[] sets_sizes) {
		int i = 0;
		for (Set<Long> c_set : all_sets) {
			if (c_set.size() != sets_sizes[i]) {
				return false;
			}
			i++;
		}
		return true;
	}

}
