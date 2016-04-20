package com.quantosauros.manager.web.process;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.quantosauros.batch.process.ProcessPrices;
import com.quantosauros.common.calendar.BusinessDayConvention;
import com.quantosauros.common.calendar.Calendar;
import com.quantosauros.common.calendar.CalendarFactory;
import com.quantosauros.common.date.Date;
import com.quantosauros.manager.model.settings.ProcessInfoModel;
import com.quantosauros.manager.service.settings.ProcessInfoService;

@Controller
public class PriceProcessController {

	private final Logger logger = Logger.getLogger(PriceProcessController.class);
	
	private ProcessInfoService processInfoService;
	
	@Autowired
	public void setProcessInfoService(ProcessInfoService processInfoService){
		this.processInfoService = processInfoService;
	}
	
	@RequestMapping(value="/process/price", method=RequestMethod.GET)
	public String priceProcessIndex(Model model){
		logger.debug("priceProcessIndex()");
		populateModel(model);
		
		return "process/priceProcess";
	}
	
	@RequestMapping(value="/process/price", method=RequestMethod.POST)
	public String priceProcessExecute(
			@RequestParam("startDt") String startDt, 
			@RequestParam("endDt") String endDt,
			@RequestParam("procId") String procId,
			Model model, final RedirectAttributes redirectAttributes){
		
		logger.debug("priceProcessExecute()");
		
		Calendar calendar = CalendarFactory.getInstance("KR", 1);
		
		Date startDate = calendar.adjustDate(Date.valueOf(startDt.replace("-", "")), 
				BusinessDayConvention.FOLLOWING);
		
		Date endDate = calendar.adjustDate(Date.valueOf(endDt.replace("-", "")),
				BusinessDayConvention.FOLLOWING);
		
		Date processDate = startDate;
		while (endDate.diff(processDate) >= 0){
			ProcessPrices processPrices = new ProcessPrices(
					processDate, procId);
			
//			processPrices.execute();
			
			processDate = calendar.adjustDate(processDate.plusDays(1), 
					BusinessDayConvention.FOLLOWING);			
		}		
		
		redirectAttributes.addFlashAttribute("css", "success");
		redirectAttributes.addFlashAttribute("msg", "Calculation Done! Process ID :" + procId); 
						
		
		
		return "redirect:/process/price";
	}
	
	private void populateModel(Model model){
		//ProcessInfoList
		List<ProcessInfoModel> processInfoList = processInfoService.selectProcessInfo();
		Map<String, String> processList = new LinkedHashMap<>();
		for (int index = 0; index < processInfoList.size(); index++){
			ProcessInfoModel processInfo = processInfoList.get(index);
			String procId = processInfo.getProcId();
			String procNM = processInfo.getProcNM();
			
			processList.put(procId, procId + ". " + procNM);			
		}
		model.addAttribute("processList", processList);
	}
	
}
