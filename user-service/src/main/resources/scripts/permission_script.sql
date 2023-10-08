-- TABLE PERMISSION USER MANAGEMENT
INSERT INTO public.permissions (id, access_level, api, category, description, "name", parent_id, status)
VALUES('6cf53724-cecb-4145-89d8-03ee33310694', 0, '', 'user', '[ROOT]-User Management', 'User Management', '', 1);
INSERT INTO public.permissions (id, access_level, api, category, description, "name", parent_id, status)
VALUES('09c14289-6b74-480d-adb3-4acffe27b6d5', 1, '', 'user', '[PARENT]-Create User', 'Create User', '6cf53724-cecb-4145-89d8-03ee33310694', 1);
INSERT INTO public.permissions (id, access_level, api, category, description, "name", parent_id, status)
VALUES('def6cec5-6287-4fbd-9dae-8ac35ca1bf59', 2, '/user/v1/create', 'user', '[CHILD]-API Create User', 'API Create User', '09c14289-6b74-480d-adb3-4acffe27b6d5', 1);
INSERT INTO public.permissions (id, access_level, api, category, description, "name", parent_id, status)
VALUES('70f365de-7109-4f45-a08a-d2d36c990df2', 1, '', 'user', '[PARENT]-Update User', 'Update User', '6cf53724-cecb-4145-89d8-03ee33310694', 1);
INSERT INTO public.permissions (id, access_level, api, category, description, "name", parent_id, status)
VALUES('375a3fa7-d80b-46e6-b1f1-7ea9d31d4823', 2, '/user/v1/update', 'user', '[CHILD]-API Update User', 'API Update User', '70f365de-7109-4f45-a08a-d2d36c990df2', 1);
INSERT INTO public.permissions (id, access_level, api, category, description, "name", parent_id, status)
VALUES('0f324f23-61c6-49d3-a3f6-edb224284437', 1, '', 'user', 'Delete User', 'Delete User', '6cf53724-cecb-4145-89d8-03ee33310694', 1);
INSERT INTO public.permissions (id, access_level, api, category, description, "name", parent_id, status)
VALUES('d5a91e52-9480-4a44-b523-88b8ae9c780b', 2, '/user/v1/delete', 'user', '[CHILD]-API Delete User', 'API Delete User', '0f324f23-61c6-49d3-a3f6-edb224284437', 1);
INSERT INTO public.permissions (id, access_level, api, category, description, "name", parent_id, status)
VALUES('d66be642-a30d-4f33-bd95-73cae189c57b', 1, '', 'user', 'View User', 'View User', '6cf53724-cecb-4145-89d8-03ee33310694', 1);
INSERT INTO public.permissions (id, access_level, api, category, description, "name", parent_id, status)
VALUES('d4b5ae8d-a56e-4b68-9f72-de89bf40220b', 2, '/user/v1/info', 'user', '[CHILD]-API View User Info', 'API View User Info', 'd66be642-a30d-4f33-bd95-73cae189c57b', 1);
INSERT INTO public.permissions (id, access_level, api, category, description, "name", parent_id, status)
VALUES('ad745ccd-cc5f-4868-bd68-63fe2d979d69', 2, '/user/v1/info-detailed', 'user', '[CHILD]-API View User Detail', 'API View User Detail', 'd66be642-a30d-4f33-bd95-73cae189c57b', 1);

-- TABLE PERMISSION ROLE MANAGEMENT
INSERT INTO public.permissions (id, access_level, api, category, description, "name", parent_id, status)
VALUES('57a840e6-3ba5-4c7d-a3ef-ec8931772a03', 0, '', 'role', '[ROOT]-Role Management', 'Role Management', '', 1);
INSERT INTO public.permissions (id, access_level, api, category, description, "name", parent_id, status)
VALUES('4b8c5b77-e802-4f4e-b67b-7eb01db58071', 1, '', 'role', '[PARENT]-Create Role', 'Create Role', '57a840e6-3ba5-4c7d-a3ef-ec8931772a03', 1);
INSERT INTO public.permissions (id, access_level, api, category, description, "name", parent_id, status)
VALUES('f3a86fc7-c73e-4d34-9170-82e0309c4c88', 2, '/role/v1/create', 'role', '[CHILD]-API Create Role', 'API Create Role', '4b8c5b77-e802-4f4e-b67b-7eb01db58071', 1);
INSERT INTO public.permissions (id, access_level, api, category, description, "name", parent_id, status)
VALUES('12414c0c-399e-4cf4-8117-1a831a14a7b3', 1, '', 'role', '[PARENT]-Update Role', 'Update Role', '57a840e6-3ba5-4c7d-a3ef-ec8931772a03', 1);
INSERT INTO public.permissions (id, access_level, api, category, description, "name", parent_id, status)
VALUES('0220cdfd-1428-4131-a8dc-c82b7e42af39', 2, '/role/v1/update', 'role', '[CHILD]-API Update Role', 'API Update Role', '12414c0c-399e-4cf4-8117-1a831a14a7b3', 1);
INSERT INTO public.permissions (id, access_level, api, category, description, "name", parent_id, status)
VALUES('fec2d802-9b2c-4f14-b078-3605a63659d6', 1, '', 'role', '[PARENT]-Delete Role', 'Delete Role', '57a840e6-3ba5-4c7d-a3ef-ec8931772a03', 1);
INSERT INTO public.permissions (id, access_level, api, category, description, "name", parent_id, status)
VALUES('6591f7f6-4dfb-4a59-a9b6-b08e65be0109', 2, '/role/v1/delete', 'role', '[CHILD]-API Delete Role', 'API Delete Role', 'fec2d802-9b2c-4f14-b078-3605a63659d6', 1);
INSERT INTO public.permissions (id, access_level, api, category, description, "name", parent_id, status)
VALUES('894f8bcd-b361-45c4-a37f-29fd61bdbd35', 1, '', 'role', 'View Role', 'View Role', '57a840e6-3ba5-4c7d-a3ef-ec8931772a03', 1);
INSERT INTO public.permissions (id, access_level, api, category, description, "name", parent_id, status)
VALUES('e945de4d-32fc-435b-8e8d-470ad799984a', 2, '/role/v1/info', 'role', '[CHILD]-API View Role Info', 'API View Role Info', '894f8bcd-b361-45c4-a37f-29fd61bdbd35', 1);
INSERT INTO public.permissions (id, access_level, api, category, description, "name", parent_id, status)
VALUES('a6b63ec2-9b78-4a44-911b-e85ce0af585e', 2, '/role/v1/info-detailed', 'role', '[CHILD]-API View Role Detail', 'API View Role Detail', '894f8bcd-b361-45c4-a37f-29fd61bdbd35', 1);
INSERT INTO public.permissions (id, access_level, api, category, description, "name", parent_id, status)
VALUES('1aa47bf2-496e-42d9-a550-6062868fc7fb', 2, '/role/v1/search', 'role', '[CHILD]-API Search Role', 'API Search Role', '894f8bcd-b361-45c4-a37f-29fd61bdbd35', 1);