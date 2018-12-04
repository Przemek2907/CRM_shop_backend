package com.przemek.zochowski.dto;



import com.przemek.zochowski.exceptions.ErrorCode;
import com.przemek.zochowski.exceptions.MyException;
import com.przemek.zochowski.model.EGuarantee;
import lombok.Data;


import javax.validation.constraints.Null;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Data
public class ProductDto {

    private Long id;
    private String name;
    private BigDecimal price;
    private ProducerDto producerDto;
    private CategoryDto categoryDto;
    private Set<EGuarantee> guarantees;

    public ProductDto (ProductDtoBuilder productDtoBuilder){
        this.id = productDtoBuilder.id;
        this.name = productDtoBuilder.name;
        this.price = productDtoBuilder.price;
        this.producerDto = productDtoBuilder.producerDto;
        this.categoryDto = productDtoBuilder.categoryDto;
        this.guarantees = productDtoBuilder.guarantees;
    }

    public static ProductDtoBuilder builder (){
        return new ProductDtoBuilder();
    }

   /* public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public ProducerDto getProducerDto() {
        return producerDto;
    }

    public void setProducerDto(ProducerDto producerDto) {
        this.producerDto = producerDto;
    }

    public CategoryDto getCategoryDto() {
        return categoryDto;
    }

    public void setCategoryDto(CategoryDto categoryDto) {
        this.categoryDto = categoryDto;
    }

    public Set<EGuarantee> getGuarantees() {
        return guarantees;
    }

    public void setGuarantees(Set<EGuarantee> guarantees) {
        this.guarantees = guarantees;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductDto that = (ProductDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(price, that.price) &&
                Objects.equals(producerDto, that.producerDto) &&
                Objects.equals(categoryDto, that.categoryDto) &&
                Objects.equals(guarantees, that.guarantees);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, producerDto, categoryDto, guarantees);
    }*/

    public static class ProductDtoBuilder {
        private Long id;
        private String name;
        private BigDecimal price;
        private ProducerDto producerDto;
        private CategoryDto categoryDto;
        private Set<EGuarantee> guarantees;

        public ProductDtoBuilder id (Long id){
            try{
                if (id== null){
                    throw new NullPointerException("ID IS NULL");
                }
                this.id = id;
                return this;
            } catch (Exception e){
                throw new MyException(ErrorCode.VALIDATION, e.getMessage());
            }
        }

        public ProductDtoBuilder name (String name){
            try{
                if (name == null){
                    throw new NullPointerException("NAME IS NULL");
                }
                if (!name.matches("[A-Z ]+")){
                    throw new IllegalArgumentException("NAME IS NOT VALID");
                }
                this.name = name;
                return this;
            } catch (Exception e){
                throw new MyException(ErrorCode.VALIDATION, e.getMessage());
            }
        }

        public ProductDtoBuilder price (BigDecimal price){
            try{
                if (price.compareTo(BigDecimal.ZERO) == -1 || price.compareTo(BigDecimal.ZERO) == 0){
                    throw new IllegalArgumentException("PRICE OF A PRODUCT HAS TO BE GREATER THAN 0");
                }
                this.price = price;
                return this;
            } catch (Exception e){
                throw new MyException(ErrorCode.VALIDATION, e.getMessage());
            }
        }

        public ProductDtoBuilder producerDto (ProducerDto producerDto) {
            try{
                if (producerDto == null){
                    throw new NullPointerException("PRODUCER IS NOT PROVIDED");
                }
                this.producerDto = producerDto;
                return this;
            } catch (Exception e){
                throw new MyException(ErrorCode.VALIDATION, e.getMessage());
            }
        }

        public ProductDtoBuilder categoryDto (CategoryDto categoryDto) {
            try{
                if (categoryDto == null){
                    throw new NullPointerException("CATEGORY IS NOT PROVIDED");
                }
                this.categoryDto = categoryDto;
            return this;
            } catch (Exception e){
                throw new MyException(ErrorCode.VALIDATION, e.getMessage());
            }
        }

        public ProductDtoBuilder guarantees (Set<EGuarantee> guarantees){
            try{
                if (guarantees.isEmpty()){
                    throw new NullPointerException("NO GUARANTEE PROVIDED FOR THE PRODUCT");
                }
                 this.guarantees = guarantees;
                return this;
            } catch (Exception e){
                throw new MyException(ErrorCode.VALIDATION, e.getMessage());
            }
        }

        public ProductDto build() {
            return new ProductDto(this);
        }
    }
}
