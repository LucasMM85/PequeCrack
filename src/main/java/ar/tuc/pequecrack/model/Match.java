package ar.tuc.pequecrack.model;

import java.time.LocalDate;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Match {
	private final ObjectProperty<LocalDate> fecha;
	private final StringProperty temporada;
	private final IntegerProperty jornada;
	private final StringProperty equipolocal;
	private final StringProperty equipovisitante;
	private final StringProperty lugar;
	private final StringProperty horaComienzo;
	private final StringProperty resultado;

	public Match(String temporada, int jornada, String equipoLocal, String equipoVisitante,String resultado){
		this.temporada = new SimpleStringProperty(temporada);
		this.jornada = new SimpleIntegerProperty(jornada);
		this.equipolocal = new SimpleStringProperty(equipoLocal);
		this.equipovisitante = new SimpleStringProperty(equipoVisitante);
		this.resultado = new SimpleStringProperty(resultado);
		
		this.fecha = new SimpleObjectProperty<LocalDate>(LocalDate.now());
		this.lugar = new SimpleStringProperty("No Especificado");
		this.horaComienzo = new SimpleStringProperty("No Especificado");
		
	}
	
	public Match(String temporada, int jornada, String equipoLocal, String equipoVisitante, LocalDate fecha, String lugar, String hora, String resultado){
		this.temporada = new SimpleStringProperty(temporada);
		this.jornada = new SimpleIntegerProperty(jornada);
		this.equipolocal = new SimpleStringProperty(equipoLocal);
		this.equipovisitante = new SimpleStringProperty(equipoVisitante);
		this.resultado = new SimpleStringProperty(resultado);		
		this.fecha = new SimpleObjectProperty<LocalDate>(fecha);
		this.lugar = new SimpleStringProperty(lugar);
		this.horaComienzo = new SimpleStringProperty(hora);
		
	}

	public final ObjectProperty<LocalDate> fechaProperty() {
		return this.fecha;
	}

	public final java.time.LocalDate getFecha() {
		return this.fechaProperty().get();
	}

	public final void setFecha(final java.time.LocalDate fecha) {
		this.fechaProperty().set(fecha);
	}

	public final StringProperty temporadaProperty() {
		return this.temporada;
	}

	public final String getTemporada() {
		return this.temporadaProperty().get();
	}

	public final void setTemporada(final String temporada) {
		this.temporadaProperty().set(temporada);
	}

	public final IntegerProperty jornadaProperty() {
		return this.jornada;
	}

	public final int getJornada() {
		return this.jornadaProperty().get();
	}

	public final void setJornada(final int jornada) {
		this.jornadaProperty().set(jornada);
	}

	public final StringProperty equipolocalProperty() {
		return this.equipolocal;
	}

	public final java.lang.String getEquipolocal() {
		return this.equipolocalProperty().get();
	}

	public final void setEquipolocal(final java.lang.String equipolocal) {
		this.equipolocalProperty().set(equipolocal);
	}

	public final StringProperty equipovisitanteProperty() {
		return this.equipovisitante;
	}

	public final java.lang.String getEquipovisitante() {
		return this.equipovisitanteProperty().get();
	}

	public final void setEquipovisitante(final java.lang.String equipovisitante) {
		this.equipovisitanteProperty().set(equipovisitante);
	}

	public final StringProperty lugarProperty() {
		return this.lugar;
	}

	public final java.lang.String getLugar() {
		return this.lugarProperty().get();
	}

	public final void setLugar(final java.lang.String lugar) {
		this.lugarProperty().set(lugar);
	}

	public final StringProperty horaComienzoProperty() {
		return this.horaComienzo;
	}

	public final java.lang.String getHoraComienzo() {
		return this.horaComienzoProperty().get();
	}

	public final void setHoraComienzo(final java.lang.String horaComienzo) {
		this.horaComienzoProperty().set(horaComienzo);
	}

	public final StringProperty resultadoProperty() {
		return this.resultado;
	}

	public final java.lang.String getResultado() {
		return this.resultadoProperty().get();
	}

	public final void setResultado(final java.lang.String resultado) {
		this.resultadoProperty().set(resultado);
	}
}
