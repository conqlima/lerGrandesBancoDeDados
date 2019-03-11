
//package challenge;

import java.util.HashMap;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.time.LocalDate;
import java.io.UnsupportedEncodingException;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.Date;
import java.text.DateFormat;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Objects;

public class Main {

	File file = new File("data.csv");
	String line = "";
	String cvsSplitBy = ",";

	private DateFormat format = new SimpleDateFormat("yyy-MM-dd");

	/*
	 * public boolean existeNaLista(List<String> lista, String palavra) { for
	 * (String c : lista) { if (c.equals(palavra)) return true; } return false; }
	 */

	// Quantas nacionalidades (coluna `nationality`) diferentes existem no arquivo?
	public int q1() {
		List<String> lista = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"))) {
			boolean primeiraLinha = true;
			while ((line = br.readLine()) != null) {
				if (primeiraLinha) {
					primeiraLinha = false;
					continue;
				}
					// use comma as separator
				String[] field = line.split(cvsSplitBy);

				boolean b = lista.contains(field[14]);
				if (!b) {
					lista.add(field[14]);
					// System.out.println(field[14]);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		//System.out.println(lista.size());
		return lista.size();
	}

	// Quantos clubes (coluna `club`) diferentes existem no arquivo?
	// Obs: Existem jogadores sem clube.
	public int q2() {
		Map<String, Integer> club = new HashMap<>();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"))) {
			boolean primeiraLinha = true;
			while ((line = br.readLine()) != null) {
				if (primeiraLinha) {
					primeiraLinha = false;
					continue;
				}
				// use comma as separator
				String[] field = line.split(cvsSplitBy);

				if (!field[3].isEmpty()) {
					club.put(field[3], 1);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		// System.out.println(club.size());
		return club.size();
	}

	// Liste o primeiro nome (coluna `full_name`) dos 20 primeiros jogadores.
	public List<String> q3() {
		List<String> lista = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"))) {
			boolean primeiraLinha = true;
			int counter = 0;
			while ((line = br.readLine()) != null) {
				if (primeiraLinha) {
					primeiraLinha = false;
					continue;
				}
				// use comma as separator
				String[] field = line.split(cvsSplitBy);

				boolean b = lista.contains(field[2]);
				if (!b) {
					lista.add(field[2]);
					// System.out.println(fullName[0]);
					counter++;
				}
				if (counter == 20)
					break;
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		// System.out.println(lista.size());
		return lista;
	}

	// Quem são os top 10 jogadores que possuem as maiores cláusulas de rescisão?
	// (utilize as colunas `full_name` e `eur_release_clause`)
	public List<String> q4() {
		List<String> lista = new ArrayList<>();
		Map<Integer, Double> unsortMap = new HashMap<>();
		Map<Integer, String> code_name = new HashMap<>();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"))) {
			boolean primeiraLinha = true;
			int counter = 0;
			while ((line = br.readLine()) != null) {
				if (primeiraLinha) {
					primeiraLinha = false;
					continue;
				}
				// use comma as separator
				String[] field = line.split(cvsSplitBy);
				if (!field[18].isEmpty()) {
					unsortMap.put(Integer.parseInt(field[0]), Double.parseDouble(field[18]));
					code_name.put(Integer.parseInt(field[0]), field[2]);

				}
			}
			// ordena map em ordem decrescente
			Map<Integer, Double> result = unsortMap.entrySet().stream()
					.sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
					.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue,
							LinkedHashMap::new));
			for (Map.Entry<Integer, Double> entry : result.entrySet()) {
				if (counter == 10)
					break;
				lista.add(code_name.get(entry.getKey()));
				// System.out.println(entry.getKey() + " : " + entry.getValue());
				counter++;

			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		// System.out.println(lista);
		// System.out.println(lista.size());
		return lista;
	}

	// Quem são os 10 jogadores mais velhos (use como critério de desempate o campo
	// `eur_wage`)?
	// (utilize as colunas `full_name` e `birth_date`)
	public List<String> q5() {
		List<String> lista = new ArrayList<>();
		Map<Integer, Integer> code_age = new HashMap<>();
		Map<Integer, Double> code_wage = new HashMap<>();
		Map<Integer, String> code_name = new HashMap<>();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(
          new FileInputStream(file), "UTF-8"))) {
			boolean primeiraLinha = true;
			int counter = 0;
			while ((line = br.readLine()) != null) {
				if (primeiraLinha) {
					primeiraLinha = false;
					continue;
				}
				// use comma as separator
				String[] field = line.split(cvsSplitBy);
				if (!field[17].isEmpty()) {
					LocalDate today = LocalDate.now();
					LocalDate birthday = LocalDate.parse(field[8]);			
					code_age.put(Integer.parseInt(field[0]), (int) (long) ChronoUnit.DAYS.between(birthday, today));
					code_wage.put(Integer.parseInt(field[0]), Double.parseDouble(field[17]));
					code_name.put(Integer.parseInt(field[0]), field[2]);
				}
			}

			// coloca o map em ordem decrescente
			Map<Integer, Integer> result1 = code_age.entrySet().stream()
					.sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
					.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue,
							LinkedHashMap::new));

			List<Integer> ages = new ArrayList<>();
			List<Integer> codes = new ArrayList<>();
			// Tira os 10 primeiros e coloca os codes em "codes" e as idades em "ages"
			for (Map.Entry<Integer, Integer> entry : result1.entrySet()) {
				if (counter == 10)
					break;
				codes.add(entry.getKey());
				ages.add(entry.getValue());
				//System.out.println(entry.getKey() + " : " + entry.getValue());
				counter++;
			}

			// usa ordenação bolha para fazer o desempate, caso houver (a idade foi calculada em dias)
			boolean flag;
			do {
				flag = false;
				for (int i = 0; i < ages.size() - 2; i++) {
					if (ages.get(i) == ages.get(i + 1)) {
						if (code_wage.get(codes.get(i)) < code_wage.get(codes.get(i + 1))) {
							Integer temp = codes.get(i + 1);
							codes.set(i + 1, codes.get(i));
							codes.set(i, temp);
							flag = true;
						}
					}
				}
			} while (flag);
			//obtem o nome do jogador pelo seu code
			for(Integer c: codes){
				lista.add(code_name.get(c));
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//System.out.println(lista);
		//System.out.println(lista.size());
		return lista;
	}

	// Conte quantos jogadores existem por idade. Para isso, construa um mapa onde
	// as chaves são as idades e os valores a contagem.
	// (utilize a coluna `age`)
	public Map<Integer, Integer> q6() {

		Map<Integer, Integer> counter = new HashMap<>();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"))) {
			boolean primeiraLinha = true;
			while ((line = br.readLine()) != null) {
				if (primeiraLinha) {
					primeiraLinha = false;
					continue;
				}

				// use comma as separator
				String[] field = line.split(cvsSplitBy);
				if (!field[6].isEmpty()) {
					int idade = Integer.parseInt(field[6]);
					Integer newValue = counter.get(idade);
					// evitar erro de mémora, pois "newValue" pode ser null
					int i = newValue != null ? newValue.intValue() : 0;
					counter.put(idade, i + 1);

				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		// for (Map.Entry<Integer, Integer> entry : counter.entrySet())
		// System.out.println(entry.getKey() + " : " + entry.getValue());

		return counter;
	}

	public static void main(String[] args) {
		Main instance = new Main();
		 instance.q1();
		 instance.q2();
		 instance.q3();
		 instance.q4();
		 instance.q5();
		 instance.q6();
	}

}
