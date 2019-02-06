package com.przemek.zochowski.services.dataFromFile;

import com.przemek.zochowski.dto.CategoryDto;
import com.przemek.zochowski.dto.ModelMapper;
import com.przemek.zochowski.model.Category;
import com.przemek.zochowski.repository.CategoryRepository;
import com.przemek.zochowski.repository.CategoryRepositoryImpl;
import com.przemek.zochowski.services.dataFromFile.converters.CategoryJsonConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;


public class CategoryJson {
    private Set<CategoryDto> categoriesDto;
    ModelMapper modelMapper = new ModelMapper();


    public Set<CategoryDto> getCategoriesDto() {
        return categoriesDto;
    }

    public void setCategoriesDto(Set<CategoryDto> categoriesDto) {
        this.categoriesDto = categoriesDto;
    }

    @Override
    public String toString() {
        return "CategoryJson{" +
                "categoriesDto=" + categoriesDto +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryJson that = (CategoryJson) o;
        return Objects.equals(categoriesDto, that.categoriesDto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoriesDto);
    }




    public List<Category> createListOfUniqueCategoriesforDB () {
        List<Category> categoryListToInsert = new ArrayList<>();
        try {
          categoryListToInsert =  new CategoryJsonConverter(Filenames.CATEGORY_JSON)
                    .fromJson().orElseThrow(IllegalStateException::new)
                    .getCategoriesDto()
                  .stream()
                  .map(modelMapper::fromCategoryDtoToCategory)
                  .collect(Collectors.toList());

          compareTheListWithTheDB(categoryListToInsert);
        } catch (Exception e) {
                e.printStackTrace();
        }
        return categoryListToInsert;
    }

   private void compareTheListWithTheDB (List<Category> categoriesToBePutToDB){
        CategoryRepository categoryRepository = new CategoryRepositoryImpl();
        List<Category> categoriesInTheDB = categoryRepository.findAll();
       System.out.println(categoriesInTheDB);
       for (Category category: categoriesInTheDB) {
               categoriesToBePutToDB.removeIf(c -> c.getName().equals(category.getName()));
           }
       System.out.println(categoriesToBePutToDB);
    }

}
