package com.imokkkk.storage.controller;

import com.imokkkk.storage.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ImOkkkk
 * @date 2023/1/9 9:23
 * @since 1.0
 */
@RestController
@RequestMapping("/storage")
public class StorageController {

    @Autowired private StorageService storageService;

    @GetMapping("/deduct")
    public void deduct(
            @RequestParam("commodityCode") String commodityCode, @RequestParam("count") int count) {
        storageService.deduct(commodityCode, count);
    }
}
