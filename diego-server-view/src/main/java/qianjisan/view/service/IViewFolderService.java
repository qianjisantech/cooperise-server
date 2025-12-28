package qianjisan.view.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import qianjisan.view.entity.ViewFolder;
import qianjisan.view.request.ViewFolderQueryRequest;
import qianjisan.view.request.ViewFolderRequest;
import qianjisan.view.vo.ViewFolderPageVO;
import qianjisan.view.vo.ViewFolderVO;

import java.util.List;

/**
 * WorkspaceViewFolder服务接口
 *
 * @author DCP Team
 * @since 2024-12-20
 */
public interface IViewFolderService extends IService<ViewFolder> {

    /**
     * 创建视图文件夹
     *
     * @param request 文件夹请求
     * @param userId 用户ID
     * @return 创建的文件夹VO
     */
    void createFolder(ViewFolderRequest request, Long userId);

    /**
     * 更新视图文件夹
     *
     * @param id     文件夹ID
     * @param request 文件夹请求
     * @return 更新后的文件夹VO
     */
    void updateFolder(Long id, ViewFolderRequest request);

    /**
     * 删除视图文件夹
     *
     * @param id 文件夹ID
     */
    void deleteFolder(Long id);

    /**
     * 根据ID查询视图文件夹
     *
     * @param id 文件夹ID
     * @return 文件夹VO
     */
    ViewFolderVO getFolderById(Long id);

    /**
     * 查询视图文件夹列表
     *
     * @return 文件夹VO列表
     */
    List<ViewFolderVO> listFolders();

    /**
     * 分页查询视图文件夹
     *
     * @param request 查询条件
     * @return 分页结果
     */
    Page<ViewFolderPageVO> pageFolder(ViewFolderQueryRequest request);
}
