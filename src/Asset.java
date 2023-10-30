public class Asset {
  public String symbol;
  public double purchasePrice;
  public int quantity;
  public int assetType;
  public Asset next;

  public Asset(String symbol, double purchasePrice, int quantity, int assetType) {
      this.symbol = symbol;
      this.purchasePrice = purchasePrice;
      this.quantity = quantity;
      this.assetType = assetType;
      this.next = null;
  }
}
