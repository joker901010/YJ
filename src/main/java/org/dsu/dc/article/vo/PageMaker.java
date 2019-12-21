package org.dsu.dc.article.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

@Getter
@Log
@ToString(exclude = "pageList")
public class PageMaker<T> {

    private Page<T> result;
    private Pageable prevPage;  
    private Pageable nextPage;  
    private Pageable currentPage; 

    private int currentPageNum;
    private int totalPageNum; 

    private List<Pageable> pageList; 

   
    public PageMaker(Page<T> result) {
        this.result = result;
        this.currentPage = result.getPageable();
        this.currentPageNum = currentPage.getPageNumber() + 1;
        this.totalPageNum = result.getTotalPages();
        this.pageList = new ArrayList<>();
        calcPages();
    }

    
    private void calcPages() {

        int tempEndNum = (int) (Math.ceil(this.currentPageNum / 10.0) * 10); 
        int startNum = tempEndNum - 9; 
        Pageable stratPage = this.currentPage;  

        for (int i = startNum; i < this.currentPageNum; i++) {
            stratPage = stratPage.previousOrFirst();
        }

        this.prevPage = stratPage.getPageNumber() <= 0 ? null : stratPage.previousOrFirst();

        log.info("startNum : " + startNum);
        log.info("tempEndNum : " + tempEndNum);
        log.info("total : " + totalPageNum);


        if (this.totalPageNum < tempEndNum) {
            tempEndNum = this.totalPageNum;
            this.nextPage = null;
        }

        log.info("modified tempEndNum : " + tempEndNum);


        for (int i = startNum; i <= tempEndNum; i++) {
            pageList.add(stratPage); 
            stratPage = stratPage.next();
        }


        this.nextPage = stratPage.getPageNumber() + 1 < totalPageNum ? stratPage : null;
    }
}
