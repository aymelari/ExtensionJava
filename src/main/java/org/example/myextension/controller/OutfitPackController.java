package org.example.myextension.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


import org.example.myextension.dto.OutfitPackRequestDto;
import org.example.myextension.dto.OutfitPackResponseDto;
import org.example.myextension.service.OutfitPackService;
import org.example.myextension.dto.DeleteOutfitPackDto;
import org.example.myextension.dto.UpdateOutfitPackDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/outfit")
public class OutfitPackController{
    private final OutfitPackService outfitPackService;

    @PostMapping("/create")
    public void createOutfit(@RequestBody OutfitPackRequestDto requestDto) {
         outfitPackService.createOutfit( requestDto);

    }

    @DeleteMapping ("/delete")
    public ResponseEntity<Void> deleteOutfitPack(@RequestBody DeleteOutfitPackDto deleteOutfitPackDto) {
        outfitPackService.deleteOutfitPack(deleteOutfitPackDto);
        return  ResponseEntity.noContent().build();
    }


    @GetMapping("/get-all{userId}")
    public ResponseEntity<List<OutfitPackResponseDto>> getAllOutfitsByUserId(@RequestBody @PathVariable Long userId) {
        return  ResponseEntity.ok( outfitPackService.getAllOutfitsByUserId(userId));
    }

    @GetMapping("/get{outfitId}")
    public ResponseEntity<OutfitPackResponseDto> getOutfitById(@PathVariable @RequestBody Long outfitId) {
        return  ResponseEntity.ok(outfitPackService.getOutfitById(outfitId));
    }

   @PutMapping("/edit")
    public ResponseEntity<Void> updateOutfitPack(@RequestBody UpdateOutfitPackDto updateOutfitPackDto){
       outfitPackService.updateOutfitPack(updateOutfitPackDto);
        return ResponseEntity.noContent().build();
   }

    }
