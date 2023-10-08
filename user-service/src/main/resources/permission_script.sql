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