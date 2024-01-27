package br.com.jdevtreinamentos.tf.util;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ModeloGrafico<T1, T2> {
	private List<T1> labels;
	private List<T2> dataset;

}
