drop table RECIPE_INGREDIENT;
drop table INGREDIENT;
drop table RECIPE;
drop table COMMENTS;
drop table FAVORITE;
drop table ACCOUNT;
drop table COCKTAIL_TAG;
drop table COCKTAIL;
drop table TAG;

CREATE TABLE tag (
    tag_id NUMBER PRIMARY KEY,
    tag_trait VARCHAR2(20) NOT NULL
);

CREATE TABLE cocktail (
    cocktail_name VARCHAR2(25) PRIMARY KEY,
    cocktail_image VARCHAR2(50),
    cocktail_easylore VARCHAR2(50),
    cocktail_lore VARCHAR2(500)
);

CREATE TABLE cocktail_tag (
    cocktail_name VARCHAR2(25),
    tag_id NUMBER,
    PRIMARY KEY (cocktail_name, tag_id),
    CONSTRAINT fk_cocktail_name_cocktail_tag FOREIGN KEY (cocktail_name) REFERENCES cocktail(cocktail_name) ON DELETE CASCADE,
    CONSTRAINT fk_tag_id_cocktail_tag FOREIGN KEY (tag_id) REFERENCES tag(tag_id) ON DELETE CASCADE
);

CREATE TABLE account (
    account_id VARCHAR2(16) PRIMARY KEY,
    account_pw VARCHAR2(16) NOT NULL,
    account_name VARCHAR2(10) NOT NULL,
    account_nickname VARCHAR2(6) NOT NULL,
    account_email VARCHAR2(40) NOT NULL
);

CREATE TABLE favorite (
    account_id VARCHAR2(16),
    cocktail_name VARCHAR2(25),
    PRIMARY KEY (account_id, cocktail_name),
    CONSTRAINT fk_account_id_favorite FOREIGN KEY (account_id) REFERENCES account(account_id) ON DELETE CASCADE,
    CONSTRAINT fk_cocktail_name_favorite FOREIGN KEY (cocktail_name) REFERENCES cocktail(cocktail_name) ON DELETE CASCADE
);

CREATE TABLE comments (
    comments_id NUMBER PRIMARY KEY,
    comments_time date,
    comments_content varchar2(100) NOT NULL,
    cocktail_name VARCHAR2(25),
    account_id VARCHAR2(16),
    CONSTRAINT fk_account_id_comments FOREIGN KEY (account_id) REFERENCES account(account_id), 
    CONSTRAINT fk_cocktail_name_comments FOREIGN KEY (cocktail_name) REFERENCES cocktail(cocktail_name)
);

CREATE TABLE recipe (
    cocktail_name VARCHAR2(25) PRIMARY KEY,
    recipe_method VARCHAR2(500) NOT NULL,
    recipe_garnish VARCHAR2(300),
    recipe_url VARCHAR2(100),
    CONSTRAINT fk_cocktail_name_recipe FOREIGN KEY (cocktail_name) REFERENCES cocktail(cocktail_name)
);

CREATE TABLE ingredient (
    ingredient_name VARCHAR2(50) PRIMARY KEY,
    ingredient_lore VARCHAR2(500),
    ingredient_image VARCHAR2(50)
);

CREATE TABLE recipe_ingredient (
    cocktail_name VARCHAR2(25),
    ingredient_name VARCHAR2(50),
    recipe_ingredient_amount VARCHAR2(30),
    PRIMARY KEY(cocktail_name, ingredient_name),
    CONSTRAINT fk_cocktail_name_recipe_ingredient FOREIGN KEY (cocktail_name) REFERENCES cocktail(cocktail_name),
    CONSTRAINT fk_ingredient_name_recipe_ingredient FOREIGN KEY (ingredient_name) REFERENCES ingredient(ingredient_name)
);
