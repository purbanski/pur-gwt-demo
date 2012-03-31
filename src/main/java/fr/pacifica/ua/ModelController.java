package fr.pacifica.ua;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.queryParser.ParseException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import fr.pacifica.ua.co.IndexCO;
import fr.pacifica.ua.mp.IndexMP;


@Controller
public class ModelController implements BeanFactoryAware {	

	BeanFactory context;


	@RequestMapping(method = RequestMethod.GET,value="/mp/search/{query}")
	public ModelAndView searchMP(@PathVariable String query,HttpServletRequest request, Model model) throws IOException, ParseException {
		IndexMP index = (IndexMP)context.getBean("indexMP");
		ModelAndView mav =  new ModelAndView("default", "listSearchResult", index.search(query));
		return mav;
	}
	
	@RequestMapping(method = RequestMethod.GET,value="/mp/dico/{query}")
	public ModelAndView searchMPDico(@PathVariable String query,HttpServletRequest request, Model model) throws IOException, ParseException {
		IndexMP index = (IndexMP)context.getBean("indexMP");
		ModelAndView mav =  new ModelAndView("default", "listIndexDico", index.searchDico(query));
		return mav;
	}
	
	
	@RequestMapping(method = RequestMethod.POST,value="/mp/index/")
	public ModelAndView indexMP (HttpServletRequest request, Model model) throws IOException, ParseException {
		
		IndexMP indexMP = (IndexMP)context.getBean("indexMP");				
		File fIndex = indexMP.getIndexDirectory();			
		if( fIndex.exists()){
			File fMove = new File(fIndex.getAbsolutePath() + "_" + System.currentTimeMillis() );
			System.out.println("move " + fIndex.getPath() + " to "+ fMove.getPath());
			FileUtils.moveDirectory(fIndex,fMove);
		}		
		File fIndexDico = indexMP.getIndexDirectoryDico();
		if( fIndexDico.exists()){
			File fDicoMove = new File(fIndexDico.getAbsolutePath() + "_" + System.currentTimeMillis() );
			System.out.println("move " + fIndex.getPath() + " to "+ fDicoMove.getPath());
			FileUtils.moveDirectory(fIndexDico,fDicoMove);
		}
		
		indexMP.indexData();
		ModelAndView mav =  new ModelAndView("default", "listIndexDico", indexMP);
		return mav;
	}

	
	@RequestMapping(method = RequestMethod.POST,value="/co/index/")
	public ModelAndView indexCO(HttpServletRequest request, Model model) throws IOException, ParseException {
		IndexCO indexCo = (IndexCO)context.getBean("indexCO");				
		File fIndex = indexCo.getIndexDirectory();			
		if( fIndex.exists()){
			File fMove = new File(fIndex.getAbsolutePath() + "_" + System.currentTimeMillis() );
			System.out.println("move " + fIndex.getPath() + " to "+ fMove.getPath());
			FileUtils.moveDirectory(fIndex,fMove);
		}		
		indexCo.indexData();
		ModelAndView mav =  new ModelAndView("default", "listIndexDico", indexCo);
		return mav;
	}
	
	@RequestMapping(method = RequestMethod.GET,value="/co/search/{query}")
	public ModelAndView searchCO(@PathVariable String query,HttpServletRequest request, Model model) throws IOException, ParseException {
		IndexCO index = (IndexCO)context.getBean("indexCO");
		ModelAndView mav =  new ModelAndView("default", "listSearchResult", index.search(query));
		return mav;
	}
	


	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.context = beanFactory;		
	}

}