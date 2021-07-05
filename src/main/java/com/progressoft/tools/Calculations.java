package com.progressoft.tools;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Calculations implements ScoringSummary {

    private final List<BigDecimal> column;
    private final List<BigDecimal> zScoreDataList;
    private final List<BigDecimal>min_max;
    BigDecimal total ;
    BigDecimal sizeOfColumn ;
    public Calculations(List<BigDecimal> column) {
        this.column = column;
        total  = new BigDecimal("0");
        sizeOfColumn = BigDecimal.valueOf(column.size());
        zScoreDataList = new ArrayList<>();
        min_max = new ArrayList<>();
        SumOfTheColumn();

    }

    @Override
    public BigDecimal mean() {

        return total.divide(BigDecimal.valueOf(column.size()), RoundingMode.HALF_EVEN).setScale(2,RoundingMode.HALF_EVEN);
    }

    @Override
    public BigDecimal standardDeviation() {


        return BigDecimal.valueOf(Math.sqrt(variance().doubleValue())).setScale(2, RoundingMode.HALF_EVEN);

    }

    @Override
    public BigDecimal variance() {
        BigDecimal valueOfVariance ;
      double var =   column.stream().map(BigDecimal::doubleValue).reduce(0.0 , (result,number)-> result + Math.pow(number-mean().doubleValue(),2));

        valueOfVariance = BigDecimal.valueOf(Math.ceil(var/column.size()));
        valueOfVariance = valueOfVariance.setScale(2,RoundingMode.HALF_EVEN);
        return valueOfVariance;


    }

    @Override
    public BigDecimal median() {
        List<BigDecimal>  sortedList =  column.stream().sorted(BigDecimal::compareTo).collect(Collectors.toList());

        return sortedList.get(sortedList.size()/2).setScale(2,RoundingMode.HALF_EVEN);
    }

    @Override
    public BigDecimal min() {

        return column.stream().min(Comparator.naturalOrder()).get().setScale(2,BigDecimal.ROUND_HALF_EVEN);
    }


    @Override
    public BigDecimal max() {

        return  column.stream().max(Comparator.naturalOrder()).get().setScale(2,BigDecimal.ROUND_HALF_EVEN);
    }

    public  List<BigDecimal> calculateZScore()
    {
        BigDecimal z  ;
        for(BigDecimal i:column)
        {
         z = i.subtract(mean()).divide(standardDeviation(),RoundingMode.HALF_EVEN);
            zScoreDataList.add(z);
        }

        return zScoreDataList;
    }

    public List<BigDecimal> min_maxNormalization()
    {
        BigDecimal z  ;
        for(BigDecimal i:column)
        {
            z = i.subtract(min()).divide(max().subtract(min()),RoundingMode.HALF_EVEN);
            min_max.add(z);
        }
        return  min_max;
    }


    private void  SumOfTheColumn()
    {
      total = column.stream().reduce(BigDecimal.ZERO,BigDecimal::add);
    }

} // end class
