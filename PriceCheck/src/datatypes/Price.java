package datatypes;

import java.util.Date;

public class Price
{
	private double price;
	private Date callDate;

	public Price(double price, Date callDate)
	{
		super();
		this.price = price;
		this.callDate = callDate;
	}

	public double getPrice()
	{
		return price;
	}

	public Date getCallDate()
	{
		return callDate;
	}

	@Override
	public String toString()
	{
		return String.format("%tT -> %.2f", getCallDate(), getPrice());
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(price);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Price other = (Price) obj;
		if (Double.doubleToLongBits(price) != Double.doubleToLongBits(other.price))
			return false;
		return true;
	}
}
