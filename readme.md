``` yaml
# YAML
openapi: 3.0.1
info:
  title: OpenAPI definition
  version: v0
servers:
- url: 	https://isentropic-road-316012.el.r.appspot.com
  description: GCP application url
paths:
  /api/v1/products/category/{category}:
    get:
      tags:
      - product-catelog-controller
      description: retrieve all products by category
      operationId: getAllProductsByCategory
      parameters:
      - name: category
        in: path
        description: product category identifier
        required: true
        schema:
          type: string
        example: Commercial
      responses:
        "404":
          description: Not Found
          content:
            '*/*':
              schema:
                type: array
                items:
                  $ref: '#/components/schemas/ProductCatelog'
        "200":
          description: Ok
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/ProductCatelog'
  /api/v1/products:
    get:
      tags:
      - product-catelog-controller
      description: retrieve all products
      operationId: getAllProducts
      responses:
        "200":
          description: Ok
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/ProductCatelog'
  /v2/api-docs:
    get:
      tags:
      - swagger-2-controller
      operationId: getDocumentation
      parameters:
      - name: group
        in: query
        required: false
        schema:
          type: string
      responses:
        "200":
          description: default response
          content:
            application/json:
              schema:
                type: string
            application/hal+json:
              schema:
                type: string
  /api/v1/products/update/{id}:
    put:
      tags:
      - product-catelog-controller
      description: update product
      operationId: updateProductCatelog
      parameters:
      - name: id
        in: path
        description: unique product identifier
        required: true
        schema:
          type: integer
          format: int64
        example: 10
      requestBody:
        content:
          application/json:
            schema:
              $ref: '#/components/schemas/ProductCatelog'
      responses:
        "404":
          description: Not Found
          content:
            '*/*':
              schema:
                $ref: '#/components/schemas/ProductCatelog'
        "200":
          description: ok
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/ProductCatelog'
  /authenticate:
    post:
      tags:
      - jwt-authentication-controller
      operationId: generateAuthenticationToken
      requestBody:
        content:
          application/json:
            schema:
              $ref: '#/components/schemas/JwtRequest'
      responses:
        "200":
          description: default response
          content:
            '*/*':
              schema:
                type: object
  /api/v1/products/create:
    post:
      tags:
      - product-catelog-controller
      description: create product
      operationId: createProduct
      requestBody:
        content:
          application/json:
            schema:
              $ref: '#/components/schemas/ProductCatelog'
      responses:
        "201":
          description: Created
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/ProductCatelog'
  /api/v1/products/{id}:
    get:
      tags:
      - product-catelog-controller
      description: retrieve all products by id
      operationId: getAllProductById
      parameters:
      - name: id
        in: path
        description: unique product identifier
        required: true
        schema:
          type: integer
          format: int64
        example: 10
      responses:
        "404":
          description: Not Found
          content:
            '*/*':
              schema:
                $ref: '#/components/schemas/ProductCatelog'
        "200":
          description: Ok
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/ProductCatelog'
components:
  schemas:
    ProductCatelog:
      required:
      - productCategory
      - productName
      type: object
      properties:
        productId:
          type: integer
          format: int64
        productCategory:
          type: string
        productName:
          type: string
        productDescription:
          type: string
        units:
          type: integer
          format: int32
    JwtRequest:
      type: object
      properties:
        username:
          type: string
        password:
          type: string
```
