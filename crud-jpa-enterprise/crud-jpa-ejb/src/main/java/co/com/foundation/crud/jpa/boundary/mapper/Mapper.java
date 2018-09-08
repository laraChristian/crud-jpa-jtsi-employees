package co.com.foundation.crud.jpa.boundary.mapper;

public interface Mapper<T, I> {

	I map(T t);
}
