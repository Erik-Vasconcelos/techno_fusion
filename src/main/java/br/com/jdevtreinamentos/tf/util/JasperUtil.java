package br.com.jdevtreinamentos.tf.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import br.com.jdevtreinamentos.tf.model.Marca;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;

public class JasperUtil<T> {

	public byte[] gerarRelatorioPdf(List<T> dados, String nomeRelatorio, Map<String, Object> params,
			ServletContext contexto) {
		try {
			JRBeanCollectionDataSource dadosJasper = new JRBeanCollectionDataSource(dados);

			InputStream report = JasperUtil.class.getResourceAsStream("/relatorios/" + nomeRelatorio);

			JasperPrint impressor = JasperFillManager.fillReport(report, params, dadosJasper);

			return JasperExportManager.exportReportToPdf(impressor);
		} catch (JRException e) {
			e.printStackTrace();
		}

		return null;
	}

	public byte[] gerarRelatorioXls(List<Marca> dados, String nomeRelatorio, Map<String, Object> params,
			ServletContext servletContext) {
		try {
			JRBeanCollectionDataSource dadosJasper = new JRBeanCollectionDataSource(dados);

			InputStream report = JasperUtil.class.getResourceAsStream("/relatorios/" + nomeRelatorio);
			JasperPrint impressor = JasperFillManager.fillReport(report, params, dadosJasper);

			ByteArrayOutputStream output = new ByteArrayOutputStream();

			JRXlsxExporter exporter = new JRXlsxExporter();
			SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
			configuration.setRemoveEmptySpaceBetweenRows(true);

			exporter.setExporterInput(new SimpleExporterInput(impressor));
			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(output));
			exporter.setConfiguration(configuration);
			exporter.exportReport();

			return output.toByteArray();
		} catch (JRException e) {
			e.printStackTrace();
		}

		return null;
	}

}
