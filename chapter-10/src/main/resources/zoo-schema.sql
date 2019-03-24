CREATE TABLE species (
  `id` INTEGER(20),
  `name` VARCHAR(255),
  `num_acres` INTEGER(20)
);

CREATE TABLE animals (
  `id` INTEGER(20),
  `species_id` INTEGER(20),
  `name` VARCHAR(255),
  `date_born` TIMESTAMP
);