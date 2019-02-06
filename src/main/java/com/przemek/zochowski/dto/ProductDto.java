package com.przemek.zochowski.dto;



import com.przemek.zochowski.exceptions.ErrorCode;
import com.przemek.zochowski.exceptions.MyException;
import com.przemek.zochowski.model.EGuarantee;
import lombok.Data;


import javax.validation.constraints.Null;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
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
                   this.guarantees = new HashSet<>(Arrays.asList(EGuarantee.valueOf("HELP_DESK")));
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
