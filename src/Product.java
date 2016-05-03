
public class Product {


	
	private int productId;
	private String productName;
	private double price;
	private String description;
	private int qty;
	
	public Product(int pi, String pn, double p, String d,int q){
		
		this.productId = pi;
		this.productName = pn;
		this.price = p;
		this.description = d;
		this.qty = q;
		
	}
	
	public int getProductId(){
		return productId;
	}
	public void setProductId(int id){
		this.productId = id;
	}
	
	public String getProductName(){
		return productName;
	}

	public void setProductName(String pn){
		this.productName = pn;
	}
	
	public double getPrice(){
		return price;
	}
	public void setPrice(){
		this.price = price;	
	}
	
	public String getDescription(){
		return description;
	}
	public void setDescription(String d){
		this.description=d;
	}
	public int getQty(){
		return qty;
	}
	public void setQty(int q){
		this.qty = q;
	}
	
	public String toString(){
		String s="";
		s+="ProductID: "+ productId +"\n";
		s+="Product Name: "+productName+"\n";
		s+="Description: "+description+"\n";
		s+="Price: "+price+"\n";
		s+="QTY: "+qty+"\n";
		return s;
	}
}



