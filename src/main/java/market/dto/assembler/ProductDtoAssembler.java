package market.dto.assembler;

import market.domain.Product;
import market.dto.ProductDTO;
import market.rest.CartRestController;
import market.rest.ProductsRestController;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

public class ProductDtoAssembler extends RepresentationModelAssemblerSupport<Product, ProductDTO> {

	public ProductDtoAssembler() {
		super(ProductsRestController.class, ProductDTO.class);
	}

	@Override
	public ProductDTO toModel(Product product) {
		ProductDTO dto = instantiateModel(product);
		dto.setProductId(product.getId());
		dto.setDistillery(product.getDistillery() == null ? null : product.getDistillery().getTitle());
		dto.setName(product.getName());
		dto.setAge(product.getAge());
		dto.setAlcohol(product.getAlcohol());
		dto.setPrice(product.getPrice());
		dto.setVolume(product.getVolume());
		dto.setDescription(product.getDescription());
		dto.setAvailable(product.isAvailable());
		dto.add(linkTo(ProductsRestController.class).withRel("products"));
		dto.add(linkTo(CartRestController.class).withRel("cart"));
		return dto;
	}

	public PageImpl<ProductDTO> toModel(Page<Product> page) {
		List<ProductDTO> dtoList = page.map(this::toModel).toList();
		return new PageImpl<>(dtoList, page.getPageable(), page.getTotalElements());
	}

	public Product dtoDomain(ProductDTO dto) {
		return new Product.Builder()
			.setName(dto.getName())
			.setAge(dto.getAge())
			.setAlcohol(dto.getAlcohol())
			.setPrice(dto.getPrice())
			.setVolume(dto.getVolume())
			.setDescription(dto.getDescription())
			.setAvailable(dto.isAvailable())
			.build();
	}
}
