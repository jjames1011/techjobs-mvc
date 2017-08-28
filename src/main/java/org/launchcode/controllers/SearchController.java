package org.launchcode.controllers;

import org.launchcode.models.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping("search")
public class SearchController {

    @RequestMapping(value = "")
    public String search(Model model) {
        model.addAttribute("columns", ListController.columnChoices);
        return "search";
    }

    // TODO #1 - Create handler to process search request and display results-DONE!


   @RequestMapping(value = "results")
    public String results(Model model, @RequestParam String searchType, @RequestParam String searchTerm) {
        //load jobs
       //create empty ArrayList for jobs that comply with searchTerm
       ArrayList<HashMap<String, String>> jobs = JobData.findAll();
       ArrayList<HashMap<String, String>> found_jobs = new ArrayList<>();

       searchTerm = searchTerm.toLowerCase();

        if (searchType.equals("all")) {
            //loop over jobs and then loop over each column looking for searchTerm...(nested for)
            for(HashMap<String,String> row:jobs) {
                for (String column : row.keySet()) {

                    String columnValue = row.get(column);
                    columnValue = columnValue.toLowerCase();

                    if (columnValue.contains(searchTerm) && !found_jobs.contains(row)) {
                        found_jobs.add(row);
                        }
                    }
                }

            model.addAttribute("columns",ListController.columnChoices);
            model.addAttribute("displayJobs", found_jobs);
            return "search";
        } else {
            //loop through each job, find searchType(category) value, if value contains searchTerm return job...
            for (HashMap<String, String> row : jobs) {
                String categoryValue = row.get(searchType);
                categoryValue = categoryValue.toLowerCase();
                //change equals() to contains() for more open search results...
                if (categoryValue.contains(searchTerm)) {
                    found_jobs.add(row);
                }
            }

            model.addAttribute("columns", ListController.columnChoices);
            model.addAttribute("displayJobs", found_jobs);
            return "search";
        }
    }
}


