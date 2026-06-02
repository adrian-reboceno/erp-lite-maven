package com.ikigaisoftware.erp_lite.domain.repositories;


import com.ikigaisoftware.erp_lite.domain.catalog.CatalogItem;
import com.ikigaisoftware.erp_lite.domain.catalog.CatalogType;

import javax.xml.catalog.Catalog;
import java.util.List;
import java.util.Optional;

public interface  CatalogRepository {

    Optional<Catalog> findByType(CatalogType type);

    List<CatalogItem> findItemsByType(CatalogType type);

    Optional<CatalogItem> findItemByTypeAndCode(CatalogType type, String code);
}

