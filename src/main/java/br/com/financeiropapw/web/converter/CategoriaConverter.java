package br.com.financeiropapw.web.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.*;

import br.com.financeiropapw.categoria.*;

@FacesConverter(forClass = Categoria.class) //A regra de funcionamento do conversos é determinada pela annotation @FacesConverter, que pode ser efetuad de duas maneiras; forClass ou value.
public class CategoriaConverter implements Converter { 
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) { //Método executado quando uma informação vem no formato texto do navegador para ser atribuída a uma propriedade na classe Bean.
		if (value != null && value.trim().length() > 0) { //Verifica se o valor da "String" são diferentes de null e o tamanho maior que 0. 
			Integer codigo = Integer.valueOf(value); //Caso seja, instancia um objeto código do tipo Integer com o valor da String.
			try {
				CategoriaRN categoriaRN = new CategoriaRN(); //Instancia um objeto da classe "CategoriaRN" através do seu construtor.
				return categoriaRN.carregar(codigo); //Retorna o código do objeto instanciado.
			} catch (Exception e) { //Caso não consiga realizar a conversão é lançada a excessão.
				throw new ConverterException(
						"Não foi possível encontrar a categoria de código " + value + ". " + e.getMessage());
			}
		}
		return null;
	}
	
	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) { //Método executado quando uma informação vem da classe Bean para ser exibida na tela. 
		if (value != null) { //Verifica se o o valor do "Objeto" é diferente de null.
			Categoria categoria = (Categoria) value; //Caso seja, será feito o cast do valor.
			return categoria.getCodigo().toString(); //E será retornado o código da categoria em formato de texto.
		}
		return "";
	}

}
