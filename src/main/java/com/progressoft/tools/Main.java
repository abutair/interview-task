package com.progressoft.tools;


import java.nio.file.Path;



public class Main  implements  Normalizer {

  private   CsvController csvController;
  private Calculations calculations;



    @Override
    public ScoringSummary zscore(Path csvPath, Path destPath, String colToStandardize) {

        csvController =  new CsvController(csvPath,colToStandardize);

        calculations = new Calculations(csvController.getSpecificColumnByTitleName());

        csvController.saveCsvFile(destPath,calculations.calculateZScore(),"_z");

        return calculations;
    }

    @Override
    public ScoringSummary minMaxScaling(Path csvPath, Path destPath, String colToNormalize) {
        csvController =  new CsvController(csvPath,colToNormalize);

        calculations = new Calculations(csvController.getSpecificColumnByTitleName());
        csvController.saveCsvFile(destPath,calculations.min_maxNormalization(),"_mm");
        return calculations;
    }



} // end class
