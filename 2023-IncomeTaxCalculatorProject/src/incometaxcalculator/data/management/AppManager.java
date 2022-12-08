package incometaxcalculator.data.management;

public interface AppManager {
    void loadTaxpayer();
    void removeTaxpayer();
    void addReceiptToTaxpayer();
    void deleteReceiptOfTaxpayer();
    void calculateTaxCharts();
}
